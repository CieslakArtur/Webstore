package com.packt.webstore.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.DeleteException;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.CategoryService;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductValidator productValidator;
	
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/all")
	public String allProducts(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String categoryId) {
		List<Product> products=productService.getProductsByCategory(categoryId);
		String categoryName=categoryService.getCategoryById(categoryId).getName();
		if(products==null||products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException(categoryId,categoryName);
		}
		model.addAttribute("products",products);
		model.addAttribute("categoryName",categoryName);
		model.addAttribute("categoryId",categoryId);
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product",productService.getProductDetails(productId));
		return "product";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct=new Product();
		
		model.addAttribute("newProduct",newProduct);
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("manufacturers",categoryService.getAllManufacturers());
		return "addProduct";
	}

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product productToBeAdded,
												BindingResult result,HttpServletRequest request,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("categories",categoryService.getAllCategories());
			model.addAttribute("manufacturers",categoryService.getAllManufacturers());
			return "addProduct";
		}
		String[] suppressedFields=result.getSuppressedFields();
		if(suppressedFields.length>0) {
			throw new RuntimeException("Próba wi¹zania niedozwolonych pól: "+StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		MultipartFile productImage = productToBeAdded.getProductImage();
		if (productImage!=null && !productImage.isEmpty()) {
		   productToBeAdded.setProductImage(productImage);
		}
		
		productService.addProduct(productToBeAdded);
		return "redirect:/products/"+productToBeAdded.getCategory();
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String getEditProductForm(@RequestParam("product") String id,Model model) {
		Product editedProduct=productService.getProductById(id);
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("manufacturers",categoryService.getAllManufacturers());
		System.out.println("PRODUCT ID UPDATE: "+editedProduct.getProductId());
		System.out.println("PRODUCT ID UPDATE: "+editedProduct.getCategory());
		System.out.println("PRODUCT ID UPDATE: "+editedProduct.getManufacturer());
		model.addAttribute("newProduct",editedProduct);
		return "addProduct";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String processEditProductForm(@ModelAttribute("newProduct") @Valid Product editedProduct,
												BindingResult result,HttpServletRequest request,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("categories",categoryService.getAllCategories());
			model.addAttribute("manufacturers",categoryService.getAllManufacturers());
			return "addProduct";
		}
		
		MultipartFile productImage = editedProduct.getProductImage();
		if (productImage!=null && !productImage.isEmpty()) {
			editedProduct.setProductImage(productImage);
		}
		
		System.out.println("PRODUCT ID UPDATE post: "+editedProduct.getProductId());
		System.out.println("PRODUCT ID UPDATE post: "+editedProduct.getCategory());
		System.out.println("PRODUCT ID UPDATE post: "+editedProduct.getManufacturer());
		if(!productService.updateProduct(editedProduct)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na edytowaæ produktu o identyfikatorze: ").append(editedProduct.getProductId());
			throw new DeleteException(editedProduct.getProductId(),sb.toString(),"/products/"+editedProduct.getCategory());
		}
		
		return "redirect:/products/"+editedProduct.getCategory();
	}
	
	@RequestMapping(value="/delete")
	public String deleteCategory(@RequestParam("product") String id,@RequestParam("category") String categoryId) {
		System.out.println("DELETE PRODUCT "+id);
		if(!productService.deleteProduct(id)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na usun¹æ produktu o identyfikatorze: ").append(id);
			throw new DeleteException(id,sb.toString(),"/products/"+categoryId);
		}
		return "redirect:/products/"+categoryId;
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
		binder.setDisallowedFields("unitsInOrder","discontinued");
		binder.setAllowedFields("name", "unitPrice", "description",
				"manufacturer", "category", "unitsInStock", "productImage","language","condition","productId");
	}
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req,ProductNotFoundException exception) {
		ModelAndView mav=new ModelAndView();
		mav.addObject("description","Brak produktu o wskazanym identyfikatorze: "+exception.getProductId());
		mav.addObject("exception",exception);
		mav.addObject("btnName","Kategorie");
		mav.addObject("returnURL","/categories");
		mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
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
