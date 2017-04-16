package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomizableItem implements AbstractMenuItem {

	private int id;
	private String name;
	private String imageUrl;
	private BigDecimal price;
	private boolean selected;
	
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


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	@Override
	public BigDecimal getPrice() {
		return price;
	}


	public CustomizableItem update(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getInt(1);
		this.name = resultSet.getString(2);
		this.imageUrl = resultSet.getString(3);
		return this;
	}

}
