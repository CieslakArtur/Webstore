package com.packt.webstore.domain;

public class Customer {
	private String customerId;
	private String name;
	private String address;
	private boolean noOfOrdersMade;
	
	public Customer() {
		super();
	}
	public Customer(String customerId,String name) {
		this.customerId=customerId;
		this.name=name;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId=customerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
	public void setAddress(String address) {
		this.address=address;
	}
	public String getAddress() {
		return address;
	}
	
	public void setNoOfOrdersMade(boolean noOfOrdersMade) {
		this.noOfOrdersMade=noOfOrdersMade;
	}
	public boolean getNoOfOrdersMade() {
		return noOfOrdersMade;
	}
}
