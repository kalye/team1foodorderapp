package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;

public class CustomizableItem implements AbstractMenuItem {

	private int id;
	private String name;
	private String imageUrl;
	private BigDecimal price;
	
	
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


	@Override
	public BigDecimal getPrice() {
		return price;
	}

}