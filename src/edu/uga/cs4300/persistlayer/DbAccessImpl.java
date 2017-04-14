package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author kalkidan
 * Class for DB access, create connection and close connection
 *
 */
public class DbAccessImpl implements DbAccessInterface {

	private static DbAccessImpl dbAccessImpl;
	
	private DbAccessImpl(){
		
	}
	//create connection object
	@Override
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName(DbAccessConfiguration.DRIVE_NAME);
			con = DriverManager.getConnection(DbAccessConfiguration.CONNECTION_URL, DbAccessConfiguration.DB_CONNECTION_USERNAME, DbAccessConfiguration.DB_CONNECTION_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	//retrieve data from db
	@Override
	public ResultSet retrieve(Connection con, String query) {
		ResultSet rset = null;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rset;
	}

	//create/insert row into table
	@Override
	public int create(Connection con, String query) {
		try {
			Statement stmt = con.createStatement();
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int create(Connection con, String query, boolean returnGeneratedId) {
		try {
			Statement stmt = con.createStatement();
			int affectedRows = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			if(!returnGeneratedId){
				return affectedRows;
			}
			if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        } 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//update table
	@Override
	public int update(Connection con, String query) {
		try {
			Statement stmt = con.createStatement();
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//delete row from table
	@Override
	public int delete(Connection con, String query) {
		try {
			Statement stmt = con.createStatement();
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//close connection object
	@Override
	public void disconnect(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//method that return singleton object
	public static DbAccessImpl getInstance(){
		if(dbAccessImpl != null){
			return dbAccessImpl;
		}
		synchronized (DbAccessImpl.class) {
			if(dbAccessImpl == null){
				synchronized (DbAccessImpl.class) {
					dbAccessImpl = new DbAccessImpl();
				}
			}
		}
		return dbAccessImpl;
	}
}
