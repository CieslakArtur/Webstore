package com.packt.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.packt.webstore.databse.DatabaseConnector;
import com.packt.webstore.domain.Orders;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.OrdersRepository;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository
public class InMemoryOrderRepository implements OrdersRepository {
	@Autowired
	ProductRepository productRepository;
	
	private Orders fillOrdersFields(ResultSet rs) throws SQLException {
		Orders order=new Orders();
		order.setId(Integer.toString(rs.getInt("o.id")));
		order.setProductId(Integer.toString(rs.getInt("o.productId")));
		order.setUnitsInOrder(rs.getInt("o.unitsInOrder"));
		order.setDate(rs.getString("o.date"));
		order.setCustomerName(rs.getString("o.customerName"));
		order.setCustomerSurname(rs.getString("o.customerSUrname"));
		order.setAddress(rs.getString("o.address"));
		order.setEmail(rs.getString("o.email"));
		order.setSessionUser(rs.getString("o.user"));
		order.setProductName(rs.getString("p.name"));
		order.setProductPrice(rs.getBigDecimal("p.unitPrice"));
		return order;
	}

	@Override
	public List<Orders> getAllOrders() {
		List<Orders> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM orders as o LEFT JOIN product as p ON p.productId=o.productId ");
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		try {
			while (rs.next()) {
				list.add(fillOrdersFields(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}
	
	@Override
	public List<Orders> getOrdersByUser(String user) {
		List<Orders> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM orders as o LEFT JOIN product as p ON p.productId=o.productId WHERE user=\'").append(user).append("\'");
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		try {
			while (rs.next()) {
				list.add(fillOrdersFields(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public boolean addOrder(Orders order) {
		boolean status = false;
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();

		Product product = productRepository.getProductById(order.getProductId());
		if(product==null) {
			System.out.println("PRODUKT == NULL");
			return false;
		}
		if (product != null) {
			if (product.getUnitsInStock() < order.getUnitsInOrder()) {
				System.out.println("UNITS IN STOCK < UNITS IN ORDER");
				return false;
			}
		}
		sb.append("INSERT INTO orders values(default,").append(order.getProductId());
		sb.append(",").append(order.getUnitsInOrder());
		sb.append(",default,\'").append(order.getCustomerName()).append("\',\'").append(order.getCustomerSurname());
		sb.append("\',\'").append(order.getAddress()).append("\',\'").append(order.getEmail());
		sb.append("\',\'").append(order.getSessionUser()).append("\')");
		System.out.println(sb.toString());
		status = conn.update(sb.toString());
		
		if(status) {
			System.out.println("UPDATE PRODUKTU");
			product.setUnitsInStock(product.getUnitsInStock()-order.getUnitsInOrder());
			status=productRepository.updateProduct(product);
		}
		
		conn.closeConnection();
		sb.delete(0, sb.length());
		return status;
	}

}
