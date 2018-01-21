package com.packt.webstore.controller;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Orders;
import com.packt.webstore.exception.DeleteException;
import com.packt.webstore.service.OrdersService;
import com.packt.webstore.service.ProductService;
@Controller
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrdersService orderService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewOrderForm(@RequestParam("id") String id, Model model) {
		Orders newOrder=new Orders();
		newOrder.setProductId(id);
		model.addAttribute("newOrder",newOrder);
		model.addAttribute("product",productService.getProductById(id));
		return "addOrder";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String processAddNewOrderForm(@ModelAttribute("newOrder") @Valid Orders orderToBeAdded,
												BindingResult result,HttpServletRequest request,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("product",productService.getProductById(orderToBeAdded.getProductId()));
			return "addOrder";
		}
		String user=request.getRemoteUser();
		System.out.println("USER: "+user);
		orderToBeAdded.setSessionUser(user);
		if(!orderService.addOrder(orderToBeAdded)) {
			StringBuilder sb=new StringBuilder();
			sb.append("Nie mo¿na z³o¿yæ zamówienia. SprawdŸ dostêpnoœæ sztuk w magazynie. ");
			throw new DeleteException(orderToBeAdded.getId(),sb.toString(),"/categories");
		}
		return "redirect:/categories";
	}
	
	@RequestMapping
	public String allOrders(HttpServletRequest request,Model model) {
		List<Orders> orders;
		String user=request.getRemoteUser();
		String title;
		if(user.equals("Admin")) {
			orders=orderService.getAllOrders();
			title="Wszystkie zamówienia w sklepie";
		}else {
			orders=orderService.getOrdersByUser(user);
			title="Twoje zamówienia";
		}
		
		model.addAttribute("orders",orders);
		model.addAttribute("user",user);
		model.addAttribute("title",title);
		return "cart";
	}
	
	@ExceptionHandler(DeleteException.class)
	public ModelAndView handleError(HttpServletRequest req,DeleteException exception) {
		ModelAndView mav=new ModelAndView();
		
		mav.addObject("description",exception.getDescription());
		mav.addObject("exception",exception);
		mav.addObject("btnName","Powrót");
		mav.addObject("returnURL",exception.getReturnURL());
		mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("deleteException");
		return mav;
	}
}