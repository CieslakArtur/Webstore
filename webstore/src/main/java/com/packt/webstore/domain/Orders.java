package com.packt.webstore.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class Orders {
	private String id;
	private String productId;
	private String productName;
	private BigDecimal productPrice;
	@Digits(integer = 8, fraction = 0, message = "{Int.Orders.unitsInOrder.validation}")
	@Min(value = 1, message = "{Min.Orders.unitsInOrder.validation}")
	private long unitsInOrder;
	private String date;
	@NotNull(message = "{NotNull.Orders.customerName.validation}")
	@Size(min = 4, max = 50, message = "{Size.Orders.name.validation}")
	private String customerName;
	@NotNull(message = "{NotNull.Orders.customerSurname.validation}")
	@Size(min = 4, max = 50, message = "{Size.Orders.name.validation}")
	private String customerSurname;
	@NotNull(message = "{NotNull.Orders.address.validation}")
	@Size(min = 4, max = 50, message = "{Size.Orders.name.validation}")
	private String address;
	@NotNull(message = "{NotNull.Orders.email.validation}")
	@Email(message = "{Email.Order.email.validation}")
	private String email;
	private String sessionUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public long getUnitsInOrder() {
		return unitsInOrder;
	}
	public void setUnitsInOrder(long unitsInOrder) {
		this.unitsInOrder = unitsInOrder;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerSurname() {
		return customerSurname;
	}
	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(String sessionUser) {
		this.sessionUser = sessionUser;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

}
