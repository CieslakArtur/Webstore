package com.packt.webstore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.exception.DeleteException;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		return "categories";
	}
	
	@ExceptionHandler(NoProductsFoundUnderCategoryException.class)
	public ModelAndView handleError(HttpServletRequest req,NoProductsFoundUnderCategoryException exception) {
		ModelAndView mav=new ModelAndView();
		mav.addObject("description","Brak produktów w kategorii: "+exception.getCategoryName());
		mav.addObject("exception",exception);
		mav.addObject("btnName","Kategorie");
		mav.addObject("returnURL","/categories");
		mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
	

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewCategoryForm(Model model) {
		Category newCategory=new Category();
		
		model.addAttribute("newCategory",newCategory);
		return "addCategory";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String processAddNewCategoryForm(@ModelAttribute("newCategory") @Valid Category categoryToBeAdded,
												BindingResult result,HttpServletRequest request) {
		if(result.hasErrors()) {
			return "addCategory";
		}
		
		MultipartFile categoryImage = categoryToBeAdded.getCategoryImage();
		if (categoryImage!=null && !categoryImage.isEmpty()) {
			categoryToBeAdded.setCategoryImage(categoryImage);
		}
		
		categoryService.addCategory(categoryToBeAdded);
		return "redirect:/categories";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String getEditCategoryForm(@RequestParam("category") String id,Model model) {
		Category editedCategory=categoryService.getCategoryById(id);
		System.out.println("CATEGORY ID UPDATE: "+editedCategory.getId());
		model.addAttribute("newCategory",editedCategory);
		return "addCategory";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String processEditCategoryForm(@ModelAttribute("newCategory") @Valid Category editedCategory,
												BindingResult result,HttpServletRequest request,Model model) {
		if(result.hasErrors()) {
			return "addCategory";
		}
		
		MultipartFile categoryImage = editedCategory.getCategoryImage();
		if (categoryImage!=null && !categoryImage.isEmpty()) {
			editedCategory.setCategoryImage(categoryImage);
		}
		
		if(!categoryService.updateCategory(editedCategory)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na edytowaæ kategorii o identyfikatorze: ").append(editedCategory.getId());
			throw new DeleteException(editedCategory.getId(),sb.toString(),"/categories");
		}
		
		return "redirect:/categories";
	}
	
	@RequestMapping(value="manufacturer/add",method=RequestMethod.GET)
	public String getAddNewManufacturerForm(Model model) {
		Manufacturer newManufacturer=new Manufacturer();
		model.addAttribute("newManufacturer",newManufacturer);
		model.addAttribute("manufacturers",categoryService.getAllManufacturers());
		return "addManufacturer";
	}
	
	@RequestMapping(value="manufacturer/add",method=RequestMethod.POST)
	public String processAddNewManufacturerForm(@ModelAttribute("newManufacturer") @Valid Manufacturer manufacturerToBeAdded,
												BindingResult result,HttpServletRequest request,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("manufacturers",categoryService.getAllManufacturers());
			return "addManufacturer";
		}
		
		categoryService.addManufacturer(manufacturerToBeAdded);
		return "redirect:/categories/manufacturer/add";
	}
	
	@RequestMapping(value="manufacturer/delete")
	public String deleteManufacturer(@RequestParam("manufacturer") String id) {
		System.out.println("DELETE MANUFACTURER "+id);
		if(!categoryService.deleteManufacturer(id)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na usun¹æ wytwórcy o identyfikatorze: ").append(id);
			sb.append("<br>SprawdŸ czy istniej¹ produkty wskazanej marki w sklepie!");
			throw new DeleteException(id,sb.toString(),"/categories/manufacturer/add");
		}
		return "redirect:/categories/manufacturer/add";
	}
	
	@RequestMapping(value="/delete")
	public String deleteCategory(@RequestParam("category") String id) {
		System.out.println("DELETE CATEGORY "+id);
		if(!categoryService.deleteCategory(id)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na usun¹æ kategorii o identyfikatorze: ").append(id);
			sb.append("<br>SprawdŸ czy istniej¹ produkty nale¿¹ce do wskazanej kategorii!");
			throw new DeleteException(id,sb.toString(),"/categories");
		}
		return "redirect:/categories";
	}
	
	@ExceptionHandler(DeleteException.class)
	public ModelAndView handleError(HttpServletRequest req,DeleteException exception) {
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("description",exception.getDescription());
		mav.addObject("exception",exception);
		mav.addObject("btnName","Wytwórcy");
		mav.addObject("returnURL",exception.getReturnURL());
		mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("deleteException");
		return mav;
	}
}
