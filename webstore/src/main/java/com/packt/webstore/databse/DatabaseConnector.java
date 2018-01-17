package com.packt.webstore.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
	private static final String host = "localhost";
	private static final String port = "3306";
	private static final String database = "webstore";
	private static final String user = "root";
	private static final String password = "password";

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://" + host + ":" + port + "/" + database
			+ "?autoReconnect=true&useSSL=false";

	private Connection conn = null;
	private PreparedStatement p_stat = null;
	private Statement stat = null;
	private ResultSet rs = null;

	public DatabaseConnector() {
		try {
			Class.forName(DatabaseConnector.DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}
		try {
			conn = DriverManager.getConnection(DB_URL, user, password);
			stat = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean execute(String qry) {
		boolean status = false;
		try {
			rs = stat.executeQuery(qry);
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	
	public ResultSet getResultSet() {
		return rs;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
