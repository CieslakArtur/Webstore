package com.packt.webstore.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private String id;
	private String name;
	private String description;
	
	@OneToMany
	@JoinColumn(name="product")
	private List<Product> phones;
	
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
}
