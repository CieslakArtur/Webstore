package com.packt.webstore.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Product;
import com.packt.webstore.service.CategoryService;
import com.packt.webstore.service.CustomerService;
import com.packt.webstore.service.OrderService;
import com.packt.webstore.service.ProductService;


@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/all")
	public String allProducts(Model model) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("webstore");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		/*Product product=entityManager.find(Product.class, 1L);
		List<Product> list=new LinkedList<>();
		list.add(product);*/
		
		/*model.addAttribute("products",getList());*/
		return "test";
	}
	
	/*public List<Product> getList(){
		System.out.println("GET LIST");
		ApplicationContext context =
	    		new ClassPathXmlApplicationContext("DispatcherServlet-context.xml");
		
		List<Product> products=new LinkedList<>();
		StringBuilder select=new StringBuilder();
		select.append("SELECT * FROM product");
		Connection conn = null;
		try {
			conn =  dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(select.toString());
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				Product product=new Product();
				product.setProductId(Integer.toString(rs.getInt("productId")));
				product.setCategory(Integer.toString(rs.getInt("category")));
				product.setName(rs.getString("name"));
				product.setUnitPrice(rs.getBigDecimal("unitPrice"));
				product.setDescription(rs.getString("description"));
				product.setManufacturer(Integer.toString(rs.getInt("manufacturerId")));
				product.setUnitsInStock(rs.getInt("unitsInStock"));
				if(rs.getString("discontinued").equals("true")) {
					product.setDiscontinued(true);
				}else {
					product.setDiscontinued(false);
				}
				product.setCondition(rs.getString("condition"));
				products.add(product);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
		return products;
	}*/	
	
	
}
