package com.packt.webstore.domain;

import javax.validation.constraints.Size;

public class Manufacturer {
	private String id;
	@Size(min = 4, max = 50, message = "{Size.Manufacturer.name.validation}")
	private String name;

	public Manufacturer() {
		super();
	}

	public Manufacturer(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

}
