package com.packt.webstore.service;

import java.util.List;

import com.packt.webstore.domain.Category;

public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategoryById(String categoryId);
}
