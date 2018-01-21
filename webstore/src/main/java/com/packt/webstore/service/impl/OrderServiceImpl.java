package com.packt.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Orders;
import com.packt.webstore.domain.repository.OrdersRepository;
import com.packt.webstore.service.OrdersService;
@Service
public class OrderServiceImpl implements OrdersService{
	@Autowired
	private OrdersRepository ordersRepository;

	@Override
	public List<Orders> getAllOrders() {
		return ordersRepository.getAllOrders();
	}

	@Override
	public boolean addOrder(Orders order) {
		return ordersRepository.addOrder(order);
	}

	@Override
	public List<Orders> getOrdersByUser(String user) {
		return ordersRepository.getOrdersByUser(user);
	}
	

}
