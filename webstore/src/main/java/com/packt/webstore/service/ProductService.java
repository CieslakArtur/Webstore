package com.packt.webstore.service;

import java.util.List;
import com.packt.webstore.domain.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Product getProductById(String productId);
	Product getProductDetails(String productId);
	List<Product> getProductsByCategory(String category);
	void addProduct(Product product);
	boolean deleteProduct(String id);
	boolean updateProduct(Product product);
}
