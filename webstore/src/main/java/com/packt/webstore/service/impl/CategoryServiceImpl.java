package com.packt.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.domain.repository.CategoryRepository;
import com.packt.webstore.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.getAllCategories();
	}
	@Override
	public Category getCategoryById(String categoryId) {
		return categoryRepository.getCategoryById(categoryId);
	}
	@Override
	public List<Manufacturer> getAllManufacturers() {
		// TODO Auto-generated method stub
		return categoryRepository.getAllManufacturers();
	}
	@Override
	public void addCategory(Category category) {
		categoryRepository.addCategory(category);
	}
	@Override
	public void addManufacturer(Manufacturer manufacturer) {
		categoryRepository.addManufacturer(manufacturer);
	}
	@Override
	public boolean deleteCategory(String id) {
		return categoryRepository.deleteCategory(id);	
	}
	@Override
	public boolean deleteManufacturer(String id) {
		return categoryRepository.deleteManufacturer(id);	
	}
	@Override
	public boolean updateCategory(Category category) {
		return categoryRepository.updateCategory(category);
	}
}
