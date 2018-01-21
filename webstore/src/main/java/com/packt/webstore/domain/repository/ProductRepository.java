package com.packt.webstore.domain.repository;

import java.util.List;
import com.packt.webstore.domain.Product;

public interface ProductRepository {
	List<Product> getAllProducts();
	Product getProductById(String productId);
	Product getProductDetails(String productId);
	List<Product> getProductByCategory(String category);
	
	void addProduct(Product product);
	boolean deleteProduct(String id);
	boolean updateProduct(Product product);
}
