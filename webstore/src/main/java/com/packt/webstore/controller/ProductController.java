package com.packt.webstore.controller;

import java.io.File;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.domain.Product;
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
		return "products";
	}
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams,Model model) {
		model.addAttribute("products",productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product",productService.getProductById(productId));
		return "product";
	}
	
	@RequestMapping("/{category}/{price}")
	public String filterProducts(@MatrixVariable(pathVar="price") Map<String,List<String>> price,@PathVariable("category") String category,
									@RequestParam("manufacturer") String manufacturer,Model model) {
		Set<Product> productByManufacturer=new HashSet<Product>();
		productByManufacturer.addAll(productService.getProductByManufacturer(manufacturer));
		System.out.println("productByManufacturer size="+productByManufacturer.size());
		Set<Product> productByCategory=new HashSet<Product>();
		productByCategory.addAll(productService.getProductsByCategory(category));
		System.out.println("productByCategory size="+productByCategory.size());
		Set<Product> productByPrice=productService.getProductsByPriceFilter(price);
		System.out.println("productByPriceFIlter size="+productByPrice.size());
		productByCategory.retainAll(productByManufacturer);
		productByCategory.retainAll(productByPrice);
		System.out.println("productByCategory after retain size="+productByCategory.size());
		model.addAttribute("products",productByCategory);
		System.out.println("filter");
		return "products";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct=new Product();
		
		model.addAttribute("newProduct",newProduct);
		model.addAttribute("categories",categoryService.getAllCategories());
		model.addAttribute("manufacturers",categoryService.getAllManufacturers());
		return "addProduct";
	}
	//Zamiennie
	/*@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewProductForm(@ModelAttribute("newProduct") Product product) {
		return "addProduct";
	}*/
	
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
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCOde() {
		return "invalidPromoCode";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
		binder.setDisallowedFields("unitsInOrder","discontinued","productId");
		binder.setAllowedFields("name", "unitPrice", "description",
				"manufacturer", "category", "unitsInStock", "productImage","language","condition");
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
}
