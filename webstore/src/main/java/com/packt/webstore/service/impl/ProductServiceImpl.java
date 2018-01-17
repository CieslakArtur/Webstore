package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		return productRepository.getProductByFilter(filterParams);
	}
	@Override
	public Product getProductById(String productId) {
		return productRepository.getProductById(productId);
	}
	@Override
	public List<Product> getProductByManufacturer(String manufacturer) {
		return productRepository.getProductByManufacturer(manufacturer);
	}
	@Override
	public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) {
		return productRepository.getProductsByPriceFilter(filterParams);
	}
	@Override
	public void addProduct(Product product) {
		productRepository.addProduct(product);
	}
}
