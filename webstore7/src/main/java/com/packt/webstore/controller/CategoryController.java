package com.packt.webstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/categories")
	public String list(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		System.out.println(categoryService.getAllCategories().get(0).getName()+"   NAME!!!");
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
}
