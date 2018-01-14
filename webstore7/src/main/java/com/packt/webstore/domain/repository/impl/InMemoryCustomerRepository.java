package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository{
	private List<Customer> listOfCustomers=new ArrayList<>();
	
	public InMemoryCustomerRepository() {
		Customer customer=new Customer("C1","James String");
		customer.setAddress("New York");
		Customer customer2=new Customer("C2","George Smith");
		customer2.setAddress("California");
		Customer customer3=new Customer("C3","Charles Norris");
		customer3.setAddress("Kentucky");
		Customer customer4=new Customer("C4","Jennifer Blue");
		customer4.setAddress("San Francisco");
		listOfCustomers.add(customer);
		listOfCustomers.add(customer2);
		listOfCustomers.add(customer3);
		listOfCustomers.add(customer4);
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return listOfCustomers;
	}

}
