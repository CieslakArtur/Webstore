package com.packt.webstore.exception;

public class CategoryNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -694354952032299567L;
	private String categoryId;
	public CategoryNotFoundException(String categoryId) {
		this.categoryId=categoryId;
	}
	public String getCategoryId() {
		return categoryId;
	}

}
