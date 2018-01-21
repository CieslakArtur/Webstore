package com.packt.webstore.service;

import java.util.List;

import com.packt.webstore.domain.Orders;

public interface OrdersService {
	List<Orders> getAllOrders();
	List<Orders> getOrdersByUser(String user);
	boolean addOrder(Orders order);
}
