package com.packt.webstore.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository productRepository;
	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.getAllProducts();
	}
	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.getProductByCategory(category);
	}
	@Override
	public Product getProductById(String productId) {
		return productRepository.getProductById(productId);
	}
	@Override
	public void addProduct(Product product) {
		productRepository.addProduct(product);
	}
	@Override
	public boolean deleteProduct(String id) {
		return productRepository.deleteProduct(id);
	}
	@Override
	public boolean updateProduct(Product product) {
		return productRepository.updateProduct(product);
	}
	@Override
	public Product getProductDetails(String productId) {
		return productRepository.getProductDetails(productId);
	}
}
