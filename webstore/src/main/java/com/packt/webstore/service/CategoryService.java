package com.packt.webstore.service;

import java.util.List;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;

public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategoryById(String categoryId);
	List<Manufacturer> getAllManufacturers();
	void addCategory(Category category);
	void addManufacturer(Manufacturer manufacturer);
}
