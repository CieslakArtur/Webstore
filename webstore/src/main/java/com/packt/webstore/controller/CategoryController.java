package com.packt.webstore.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.databse.DatabaseConnector;
import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.domain.Product;
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
}
