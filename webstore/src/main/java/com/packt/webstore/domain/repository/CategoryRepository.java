package com.packt.webstore.domain.repository;

import java.util.List;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.domain.Product;

public interface CategoryRepository {
	List<Category> getAllCategories();
	Category getCategoryById(String categoryId);
	List<Manufacturer> getAllManufacturers();
	void addCategory(Category category);
	void addManufacturer(Manufacturer manufacturer);
}
