package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.repository.CategoryRepository;
import com.packt.webstore.exception.CategoryNotFoundException;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository{
	private List<Category> listOfCategories=new ArrayList<>();
	
	public InMemoryCategoryRepository() {
		Category category=new Category("Cat1","Laptops");
		category.setDescription("Opis kategorii Laptopy");
		Category category2=new Category("Cat2","Tablets");
		category2.setDescription("Opis kategorii Tablety");
		Category category3=new Category("Cat3","Phones");
		category3.setDescription("Opis kategorii Telefony");
		Category category4=new Category("Cat4","Games");
		category4.setDescription("Opis kategorii Gry");
		listOfCategories.add(category);
		listOfCategories.add(category2);
		listOfCategories.add(category3);
		listOfCategories.add(category4);
	}
	
	@Override
	public List<Category> getAllCategories() {
		return listOfCategories;
	}

	@Override
	public Category getCategoryById(String categoryId) {
		Category categoryById=null;
		for(Category category:listOfCategories) {
			if(category!=null && category.getId()!=null && category.getId().equals(categoryId)) {
				categoryById=category;
				break;
			}
		}
		if(categoryById==null) {
			throw new CategoryNotFoundException(categoryId);
		}
		return categoryById;
	}
}
