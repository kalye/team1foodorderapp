package edu.uga.cs4300.objectlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuCategory {

	private int id;
	private String name;
	private String imageUrl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public MenuCategory update(ResultSet resultSet) throws SQLException{
		this.id = resultSet.getInt(1);
		this.name = resultSet.getString(2);
		this.imageUrl = resultSet.getString(3);
		return this;
	}
	
}
