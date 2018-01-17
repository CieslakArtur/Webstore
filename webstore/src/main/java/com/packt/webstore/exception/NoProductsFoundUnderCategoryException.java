package com.packt.webstore.exception;

public class NoProductsFoundUnderCategoryException extends RuntimeException {
	private static final long serialVersionUID = -6943549520245645667L;
	private String categoryId;
	private String categoryName;

	public NoProductsFoundUnderCategoryException(String categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
