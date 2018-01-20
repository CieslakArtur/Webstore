package com.packt.webstore.domain;

import java.util.List;

import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class Category {
	
	private String id;
	@Size(min = 4, max = 50, message = "{Size.Category.name.validation}")
	private String name;
	private String description;
	@JsonIgnore
	private MultipartFile categoryImage;
	String base64Image;
	
	public Category() {
		super();
	}
	
	public Category(String id,String name) {
		this.id=id;;
		this.name=name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(MultipartFile categoryImage) {
		this.categoryImage = categoryImage;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
}
