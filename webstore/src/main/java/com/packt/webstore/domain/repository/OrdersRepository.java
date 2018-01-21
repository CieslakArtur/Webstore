package com.packt.webstore.domain.repository;

import java.util.List;

import com.packt.webstore.domain.Orders;

public interface OrdersRepository {
	List<Orders> getAllOrders();
	List<Orders> getOrdersByUser(String user);
	boolean addOrder(Orders order);
}
