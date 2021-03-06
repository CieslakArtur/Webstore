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
import com.packt.webstore.domain.Category;
import com.packt.webstore.domain.Manufacturer;
import com.packt.webstore.domain.repository.CategoryRepository;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {
	private Category fillCategoryFields(ResultSet rs) throws SQLException {
		Category category = new Category(Integer.toString(rs.getInt("id")), rs.getString("name"));
		category.setDescription(rs.getString("description"));
		if (rs.getBytes("img") != null) {
			System.out.println("Found image for " + category.getName());
			byte[] encodeBase64 = Base64.encode(rs.getBytes("img"));
			try {
				String base64Encoded = new String(encodeBase64, "UTF-8");
				category.setBase64Image(base64Encoded);
				System.out.println("Image set");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Not found image for " + category.getName());
		}
		return category;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM category");
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		try {
			while (rs.next()) {
				list.add(fillCategoryFields(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public Category getCategoryById(String categoryId) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM category WHERE id=").append(categoryId);
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		Category category = null;
		if (rs == null) {
			return category;
		}
		try {
			while (rs.next()) {
				category = fillCategoryFields(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return category;
	}

	@Override
	public List<Manufacturer> getAllManufacturers() {
		List<Manufacturer> list = new LinkedList<>();
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM manufacturer");
		conn.execute(sb.toString());
		ResultSet rs = conn.getResultSet();
		if (rs == null) {
			return list;
		}
		Manufacturer manufacturer;
		try {
			while (rs.next()) {
				manufacturer = new Manufacturer(Integer.toString(rs.getInt("id")), rs.getString("name"));
				list.add(manufacturer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.closeConnection();
		sb.delete(0, sb.length());
		return list;
	}

	@Override
	public void addCategory(Category category) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO category values(default,\'").append(category.getName());
		sb.append("\',\'").append(category.getDescription()).append("\'");
		if (!category.getCategoryImage().isEmpty()) {
			sb.append(",?)");
			System.out.println(sb.toString());
			InputStream inputStream = null;
			try {
				PreparedStatement p_stat = (PreparedStatement) conn.getConnection().prepareStatement(sb.toString());
				inputStream = category.getCategoryImage().getInputStream();
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
	}

	@Override
	public void addManufacturer(Manufacturer manufacturer) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO manufacturer values(default,\'").append(manufacturer.getName()).append("\')");
		conn.update(sb.toString());
		conn.closeConnection();
		System.out.println(sb.toString());
	}

	@Override
	public boolean deleteCategory(String id) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM category WHERE id=").append(id);
		return conn.update(sb.toString());
	}

	@Override
	public boolean deleteManufacturer(String id) {
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM manufacturer WHERE id=").append(id);
		return conn.update(sb.toString());
	}

	@Override
	public boolean updateCategory(Category category) {
		boolean status=false;
		DatabaseConnector conn = new DatabaseConnector();
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE category ");
		sb.append("SET name=\'").append(category.getName()).append("\'");
		sb.append(", description=\'").append(category.getDescription()).append("\'");
		if(!category.getCategoryImage().isEmpty()) {
			sb.append(", img=?");
			sb.append(" WHERE id=").append(category.getId());
			System.out.println(sb.toString());
			InputStream inputStream = null;
			int i=0;
			try {
				PreparedStatement p_stat = (PreparedStatement) conn.getConnection().prepareStatement(sb.toString());
				inputStream = category.getCategoryImage().getInputStream();
				p_stat.setBinaryStream(1, inputStream);
				i=p_stat.executeUpdate();
				if(i>0) {
					status=true;
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
		}else {
			sb.append(" WHERE id=").append(category.getId());
			System.out.println(sb.toString());
			status=conn.update(sb.toString());
		}
		return status;
	}
}
