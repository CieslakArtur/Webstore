package com.packt.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.databse.DatabaseConnector;
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
			List<Category> list=new LinkedList<>();
			DatabaseConnector conn=new DatabaseConnector();
			StringBuilder sb=new StringBuilder();
			sb.append("SELECT * FROM category");
			conn.execute(sb.toString());
			ResultSet rs=conn.getResultSet();
			if(rs==null) {
				return list;
			}
			Category category;
			try {
				while(rs.next()){
					category=new Category(Integer.toString(rs.getInt("id")),rs.getString("name"));
					category.setDescription(rs.getString("description"));
					list.add(category);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn.closeConnection();
			sb.delete(0, sb.length());
			return list;
	}

	@Override
	public Category getCategoryById(String categoryId) {
		DatabaseConnector conn=new DatabaseConnector();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT * FROM category WHERE id=").append(categoryId);
		conn.execute(sb.toString());
		ResultSet rs=conn.getResultSet();
		Category category=null;
		if(rs==null) {
			return category;
		}
		try {
			while(rs.next()){
				category=new Category(Integer.toString(rs.getInt("id")),rs.getString("name"));
				category.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return category;
	}
}
