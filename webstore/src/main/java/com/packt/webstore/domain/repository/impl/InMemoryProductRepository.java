package com.packt.webstore.domain.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Repository;
import com.mysql.jdbc.PreparedStatement;
import com.packt.webstore.databse.DatabaseConnector;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository
public class InMemoryProductRepository implements ProductRepository {

	private Product fillProductFields(ResultSet rs) throws SQLException {
		Product product = new Product(Integer.toString(rs.getInt("productId")), rs.getString("name"),
				rs.getBigDecimal("unitPrice"));
		product.setCategory(Integer.toString(rs.getInt("categoryId")));
		product.setDescription(rs.getString("description"));
		product.setManufacturer(Integer.toString(rs.getInt("manufacturerId")));
		product.setUnitsInStock(rs.getInt("unitsInStock"));
		if (rs.getString("discontinued").equals("true")) {
			product.setDiscontinued(true);
		} else {
			product.setDiscontinued(false);
		}
		product.setCondition(rs.getString("condition"));

		if (rs.getBytes("img") != null) {
			// InputStream inputStream=rs.getBinaryStream("img");
			System.out.println("Found image for " + product.getName());
			byte[] encodeBase64 = Base64.encode(rs.getBytes("img"));
			try {
				String base64Encoded = new String(encodeBase64, "UTF-8");
				product.setBase64Image(base64Encoded);
				System.out.println("Image set");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Not found image for " + product.getName());
		}
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM product");
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		try {
			while (rs.next()) {
				list.add(fillProductFields(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public Product getProductDetails(String productId) {
		System.out.println("PRODUCT ID=" + productId);
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM product as p LEFT JOIN manufacturer as m ON p.manufacturerId=m.id");
		sb.append(" LEFT JOIN category as c ON p.categoryId=c.id ");
		sb.append(" WHERE p.productId=").append(productId);
		System.out.println(sb.toString());
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		Product product = null;
		if (rs == null) {
			return product;
		}
		try {
			while (rs.next()) {
				product = fillProductFields(rs);
				product.setManufacturer(rs.getString("m.name"));
				product.setCategory(rs.getString("c.name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return product;
	}

	@Override
	public Product getProductById(String productId) {
		System.out.println("PRODUCT ID=" + productId);
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM product ");
		sb.append(" WHERE productId=").append(productId);
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		Product product = null;
		if (rs == null) {
			return product;
		}
		try {
			while (rs.next()) {
				product = fillProductFields(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return product;
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		List<Product> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM product WHERE categoryId=").append(category);
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		try {
			while (rs.next()) {
				list.add(fillProductFields(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public void addProduct(Product product) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO product values(default,").append(product.getCategory()).append(",\'")
				.append(product.getName());
		sb.append("\',").append(product.getUnitPrice()).append(",\'").append(product.getDescription());
		sb.append("\',").append(product.getManufacturer()).append(",").append(product.getUnitsInStock())
				.append(",default");
		if (product.getCondition().equals("used")) {
			sb.append(",\'used\'");
		} else {
			sb.append(",default");
		}
		if (!product.getProductImage().isEmpty()) {
			// Get bytes
			// use methods to append new product
			sb.append(",?)");
			System.out.println(sb.toString());
			InputStream inputStream = null;
			try {
				PreparedStatement p_stat = (PreparedStatement) conn.getConnection().prepareStatement(sb.toString());
				inputStream = product.getProductImage().getInputStream();
				p_stat.setBinaryStream(1, inputStream);
				p_stat.executeUpdate();
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn.closeConnection();
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			sb.append(",null)");
			conn.update(sb.toString());
			conn.closeConnection();
			System.out.println(sb.toString());
		}
		sb.delete(0, sb.length());
	}

	@Override
	public boolean deleteProduct(String id) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM product WHERE productId=").append(id);
		return conn.update(sb.toString());
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean status = false;
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		System.out.println("Update-1");
		System.out.println("Produkt: "+product.getName());
		sb.append("UPDATE product ");
		sb.append("SET categoryId=").append(product.getCategory());
		sb.append(" ,name=\'").append(product.getName()).append("\'");
		sb.append(", unitPrice=").append(product.getUnitPrice());
		sb.append(", description=\'").append(product.getDescription()).append("\'");
		sb.append(", manufacturerId=").append(product.getManufacturer());
		sb.append(", unitsInStock=").append(product.getUnitsInStock());
		sb.append(", `condition`=\'").append(product.getCondition()).append("\'");
		
		if(product.getProductImage()==null) {
			sb.append(" WHERE productId=").append(product.getProductId());
			System.out.println(sb.toString());
			status = conn.update(sb.toString());
			return status;
		}

		if (!product.getProductImage().isEmpty()) {
			sb.append(", img=?");
			sb.append(" WHERE productId=").append(product.getProductId());
			System.out.println(sb.toString());
			InputStream inputStream = null;
			int i = 0;
			try {
				PreparedStatement p_stat = (PreparedStatement) conn.getConnection().prepareStatement(sb.toString());
				inputStream = product.getProductImage().getInputStream();
				p_stat.setBinaryStream(1, inputStream);
				i = p_stat.executeUpdate();
				if (i > 0) {
					status = true;
				}
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				conn.closeConnection();
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			sb.append(" WHERE productId=").append(product.getProductId());
			System.out.println(sb.toString());
			status = conn.update(sb.toString());
		}
		return status;
	}
}
