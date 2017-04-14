package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MenuItem implements AbstractMenuItem {
	
	private int id;
	private int categoryId;
	private String name;
	private String description;
	private boolean hasToppings;
	private boolean hasSide;
	private boolean customizable;
	private String imageUrl;
	private BigDecimal price;
	private List<Topping> toppings;
	private List<Side> sides;
	private List<CustomizableItem> customizableItem;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isHasToppings() {
		return hasToppings;
	}
	public void setHasToppings(boolean hasToppings) {
		this.hasToppings = hasToppings;
	}
	public boolean isHasSide() {
		return hasSide;
	}
	public void setHasSide(boolean hasSide) {
		this.hasSide = hasSide;
	}
	public boolean isCustomizable() {
		return customizable;
	}
	public void setCustomizable(boolean customizable) {
		this.customizable = customizable;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<Topping> getToppings() {
		return toppings;
	}
	public void setToppings(List<Topping> toppings) {
		this.toppings = toppings;
	}
	public List<Side> getSides() {
		return sides;
	}
	public void setSides(List<Side> sides) {
		this.sides = sides;
	}
	public List<CustomizableItem> getCustomizableItem() {
		return customizableItem;
	}
	public void setCustomizableItem(List<CustomizableItem> customizableItem) {
		this.customizableItem = customizableItem;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Override
	public BigDecimal getPrice() {
		return price;
	}
	public MenuItem update(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getInt(1);
		this.categoryId = resultSet.getInt(2);
		this.name = resultSet.getString(3);
		this.description = resultSet.getString(4);
		this.hasToppings = resultSet.getBoolean(5);
		this.hasSide = resultSet.getBoolean(6);
		this.customizable = resultSet.getBoolean(7);
		this.price = resultSet.getBigDecimal(8);
		this.imageUrl = resultSet.getString(9);
		return this;
	}

}
