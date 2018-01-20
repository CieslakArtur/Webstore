package com.packt.webstore.domain.repository;

import java.util.List;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;

public interface CategoryRepository {
	List<Category> getAllCategories();
	Category getCategoryById(String categoryId);
	List<Manufacturer> getAllManufacturers();
}
