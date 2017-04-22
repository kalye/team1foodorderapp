package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;

public class Cart {

	private Order order;
	private PaymentInfo paymentInfo;
	
	public Cart(){
		this.order = new Order();
		this.paymentInfo = new PaymentInfo();
	}
	public Cart(Order order, PaymentInfo paymentInfo) {
		super();
		this.order = order;
		this.paymentInfo = paymentInfo;
	}
	public void addOrderItem(OrderItem orderItem){
		order.addOrderItem(orderItem);
	}
	public void removeOrderItem(OrderItem orderItem){
		order.removeOrderItem(orderItem);
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public BigDecimal getTotalPrice() {
		return order.getTotalPrice();
	}
	public BigDecimal getTax(){
		return order.getTax();
	}
	public BigDecimal getSubTotalPrice() {
		return order.getSubTotalPrice();
	}
	public boolean isEmpty(){
		return order != null && CollectionUtils.isNotEmpty(order.getOrderItems());
	}
	
}
