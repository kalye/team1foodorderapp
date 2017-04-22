package edu.uga.cs4300.objectlayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class OrderItem {
	
	private int itemNumber;
	private int size;
	private MenuItem menuItem;
	private boolean itemCustomized;
	private boolean hasExtraCustomizedItem;
	private List<Topping> selectedToppings = new ArrayList<>();
	private List<Side> selectedSides = new ArrayList<>();
	private List<CustomizableItem> selectedCustomizableItem = new ArrayList<>();
	private List<CustomizableItem> selectedExtraCustomizableItem = new ArrayList<>();
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public boolean isItemCustomized() {
		return itemCustomized;
	}
	public void setItemCustomized(boolean itemCustomized) {
		this.itemCustomized = itemCustomized;
	}
	public boolean isHasExtraCustomizedItem() {
		return hasExtraCustomizedItem;
	}
	public void setHasExtraCustomizedItem(boolean hasExtraCustomizedItem) {
		this.hasExtraCustomizedItem = hasExtraCustomizedItem;
	}
	public List<Topping> getSelectedToppings() {
		return selectedToppings;
	}
	public void setSelectedToppings(List<Topping> selectedToppings) {
		this.selectedToppings = selectedToppings;
	}
	public List<Side> getSelectedSides() {
		return selectedSides;
	}
	public void setSelectedSides(List<Side> selectedSides) {
		this.selectedSides = selectedSides;
	}
	public List<CustomizableItem> getSelectedCustomizableItem() {
		return selectedCustomizableItem;
	}
	public void setSelectedCustomizableItem(List<CustomizableItem> selectedCustomizableItem) {
		this.selectedCustomizableItem = selectedCustomizableItem;
	}
	public List<CustomizableItem> getSelectedExtraCustomizableItem() {
		return selectedExtraCustomizableItem;
	}
	public void setSelectedExtraCustomizableItem(List<CustomizableItem> selectedExtraCustomizableItem) {
		this.selectedExtraCustomizableItem = selectedExtraCustomizableItem;
	}
	
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if(menuItem != null){
			price =  price.add(menuItem.getPrice());
		}
		if(CollectionUtils.isNotEmpty(selectedToppings)){
			for(Topping toping: selectedToppings){
				price = price.add(toping.getPrice());
			}
		}
		if(CollectionUtils.isNotEmpty(selectedSides)){
			for(Side side: selectedSides){
				price = price.add(side.getPrice());
			}
		}
		if(CollectionUtils.isNotEmpty(selectedExtraCustomizableItem)){
			for(CustomizableItem customizableItem: selectedExtraCustomizableItem){
				price = price.add(customizableItem.getPrice());
			}
		}
		return price;
	}
	
	public String getItemDescription(){
		StringBuilder builder = new StringBuilder();
		if(menuItem != null){
			builder.append(menuItem.getName());
		}
		if(CollectionUtils.isNotEmpty(selectedCustomizableItem)){
			builder.append("\nCustomized items: ");
			for(CustomizableItem customizableItem: selectedCustomizableItem){
				builder.append("\n").append(customizableItem.getName());
			}
		}
		if(CollectionUtils.isNotEmpty(selectedExtraCustomizableItem)){
			builder.append("\nExtras items: ");
			for(CustomizableItem extraCustomizableItem: selectedExtraCustomizableItem){
				builder.append("\n").append(extraCustomizableItem.getName());
			}
		}
		if(CollectionUtils.isNotEmpty(selectedToppings)){
			builder.append("\nSelected Toppings: ");
			for(Topping toping: selectedToppings){
				builder.append("\n").append(toping.getName());
			}
		}
		if(CollectionUtils.isNotEmpty(selectedSides)){
			builder.append("\nSelected Sides: ");
			for(Side side: selectedSides){
				builder.append("\n").append(side.getName());
			}
		}
		
		return builder.toString();
	}
	

}