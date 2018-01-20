package com.packt.webstore.domain.repository.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.InflaterInputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mysql.jdbc.PreparedStatement;
import com.packt.webstore.databse.DatabaseConnector;
import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository {
	private List<Product> listOfProducts = new ArrayList<Product>();

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
		Product product;
		try {
			while (rs.next()) {
				product = new Product(Integer.toString(rs.getInt("productId")), rs.getString("name"),
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
				InputStream inputStream=rs.getBinaryStream("img");

				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public Product getProductById(String productId) {
		System.out.println("PRODUCT ID=" + productId);
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM product as p LEFT JOIN manufacturer as m ON p.categoryId=m.id");
		sb.append(" WHERE p.productId=").append(productId);
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		Product product = null;
		if (rs == null) {
			return product;
		}
		try {
			while (rs.next()) {
				product = new Product(Integer.toString(rs.getInt("p.productId")), rs.getString("p.name"),
						rs.getBigDecimal("p.unitPrice"));
				product.setCategory(Integer.toString(rs.getInt("p.categoryId")));
				System.out.println(product.getCategory());
				product.setDescription(rs.getString("p.description"));
				product.setManufacturer(rs.getString("m.name"));
				product.setUnitsInStock(rs.getInt("p.unitsInStock"));
				if (rs.getString("p.discontinued").equals("true")) {
					product.setDiscontinued(true);
				} else {
					product.setDiscontinued(false);
				}
				product.setCondition(rs.getString("p.condition"));
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
		Product product;
		try {
			while (rs.next()) {
				product = new Product(Integer.toString(rs.getInt("productId")), rs.getString("name"),
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
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public Set<Product> getProductByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productByBrand = new HashSet<Product>();
		Set<Product> productByCategory = new HashSet<Product>();
		// keySet zwraca zbiór kluczy Mapy (tutaj zbior Stringów)
		Set<String> criterias = filterParams.keySet();
		if (criterias.contains("brand")) {
			for (String brandName : filterParams.get("brand")) {
				for (Product product : listOfProducts) {
					if (brandName.equalsIgnoreCase(product.getManufacturer())) {
						productByBrand.add(product);
					}
				}
			}
		}
		if (criterias.contains("category")) {
			for (String category : filterParams.get("category")) {
				productByCategory.addAll(this.getProductByCategory(category));
			}
		}
		// Zachowuje tylko te elementy ktore nale¿¹ do kolekcji 'productByBrand'
		productByCategory.retainAll(productByBrand);
		return productByCategory;
	}

	@Override
	public List<Product> getProductByManufacturer(String manufacturer) {
		List<Product> productsByManufacturer = new ArrayList<Product>();
		System.out.println("Maniufaturer: " + manufacturer);
		for (Product product : listOfProducts) {
			if (manufacturer.equalsIgnoreCase(product.getManufacturer())) {
				productsByManufacturer.add(product);
			}
		}
		return productsByManufacturer;
	}

	@Override
	public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) {
		Set<Product> lowPrice = new HashSet<Product>();
		Set<Product> highPrice = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();
		if (criterias.contains("low")) {
			for (String low_price : filterParams.get("low")) {
				BigDecimal price = new BigDecimal(low_price);
				System.out.println("Price low=" + price);
				for (Product product : listOfProducts) {
					int res = price.compareTo(product.getUnitPrice());
					if (res <= 0) {
						lowPrice.add(product);
						System.out.println("Dodano low: " + lowPrice.size());
					}
				}
			}
		} else {
			lowPrice.addAll(listOfProducts);
		}
		if (criterias.contains("high")) {
			for (String high_price : filterParams.get("high")) {
				BigDecimal price = new BigDecimal(high_price);
				System.out.println("Price high=" + price);
				for (Product product : listOfProducts) {
					int res = price.compareTo(product.getUnitPrice());
					if (res >= 0) {
						highPrice.add(product);
						System.out.println("Dodano high: " + highPrice.size());
					}
				}
			}
		} else {
			lowPrice.addAll(listOfProducts);
		}
		lowPrice.retainAll(highPrice);
		System.out.println("AFter retain, lowPrice Size=" + lowPrice.size());
		return lowPrice;
	}

	@Override
	public void addProduct(Product product) {
		boolean status = false;
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
				//p_stat.executeUpdate();
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
			//status = conn.update(sb.toString());
			conn.closeConnection();
			System.out.println(sb.toString());
		}
		sb.delete(0, sb.length());
	}
}
