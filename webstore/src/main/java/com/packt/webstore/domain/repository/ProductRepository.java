package com.packt.webstore.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductRepository {
	List<Product> getAllProducts();
	Product getProductById(String productId);
	List<Product> getProductByCategory(String category);
	Set<Product> getProductByFilter(Map<String,List<String>> filterParams);
	List<Product> getProductByManufacturer(String manufacturer);
	Set<Product> getProductsByPriceFilter(Map<String,List<String>> filterParams);
	
	void addProduct(Product product);
}
