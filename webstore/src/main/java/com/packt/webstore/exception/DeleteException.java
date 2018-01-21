package com.packt.webstore.exception;

public class DeleteException extends RuntimeException {
	private static final long serialVersionUID = -6943549520245645667L;
	private String categoryId;
	private String description;
	private String returnURL;

	public DeleteException(String categoryId, String description, String returnURL) {
		this.categoryId = categoryId;
		this.description=description;
		this.returnURL=returnURL;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	
	

}
