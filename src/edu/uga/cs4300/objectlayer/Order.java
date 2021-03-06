package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class Order {
	
	private int orderNumber;

	private List<OrderItem> orderItems = new ArrayList<>();
	
	private Address shippingAddress = new Address();
	
	public void addOrderItem(OrderItem orderItem){
		orderItems.add(orderItem);
	}
	public void removeOrderItem(OrderItem orderItem){
		orderItems.remove(orderItem);
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public BigDecimal getSubTotalPrice() {
		BigDecimal price = new BigDecimal(0);
		if(CollectionUtils.isNotEmpty(orderItems)){
			for(OrderItem orderItem: orderItems){
				price = price.add(orderItem.getPrice());
			}
		}
		return  price;
	}
	public BigDecimal getTotalPrice(){
		BigDecimal price = new BigDecimal(0);
		price = price.add(getSubTotalPrice());
		price = price.add(price.multiply(new BigDecimal(0.07)));
		return price;
	}
	public BigDecimal getTax() {
		BigDecimal price = new BigDecimal(0);
		price = price.add(getSubTotalPrice());
		price = price.multiply(new BigDecimal(0.07));
		return price;
	}
	public String getItemDescription(){
		StringBuilder builder = new StringBuilder();
		if(CollectionUtils.isNotEmpty(orderItems)){
			int count = 1;
			builder.append("Items:\n");
			for(OrderItem orderItem: orderItems){
				builder.append(count).append(":\n");
				builder.append(orderItem.getItemDescription());
			}
		} else {
			builder.append("There is no item in this order.");
		}
		return builder.toString();
	}
	public void changeSize(int itemNumber, int size) {
		for(OrderItem orderItem: orderItems){
			if(orderItem.getItemNumber() == itemNumber){
				orderItem.setSize(size);
				return;
			}
		}
	}
	public void removeOrderItem(int itemNumber) {
		OrderItem orderItemTobeRemoved = getOrderItem(itemNumber);
		if(orderItemTobeRemoved != null){
			orderItems.remove(orderItemTobeRemoved);
		}
	}
	public OrderItem getOrderItem(int itemNumber) {
		OrderItem orderItemSearched = null;
		for(OrderItem orderItem: orderItems){
			if(orderItem.getItemNumber() == itemNumber){
				orderItemSearched = orderItem;
				break;
			}
		}
		return orderItemSearched;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
}
