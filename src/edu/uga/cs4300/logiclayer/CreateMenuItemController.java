package edu.uga.cs4300.logiclayer;

import java.util.List;

import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.MenuItem;
import edu.uga.cs4300.objectlayer.Side;
import edu.uga.cs4300.objectlayer.Topping;
import edu.uga.cs4300.persistlayer.OrderPersistImp;

public class CreateMenuItemController {

	private OrderPersistImp orderPersistImp = OrderPersistImp.getInstance();

	private static CreateMenuItemController createMenuItemController;
	
	public List<MenuCategory> getAllCatagories(){
		return orderPersistImp.getAllCatagories();
	}
	public int updateCategory(MenuCategory category){
		return orderPersistImp.updateCategory(category);
	}
	public int createCategory(MenuCategory category){
		return orderPersistImp.createCategory(category);
	}

	// method to return single instance of this class
	public static CreateMenuItemController getInstance() {
		if (createMenuItemController != null) {
			return createMenuItemController;
		}
		synchronized (CreateMenuItemController.class) {
			if (createMenuItemController == null) {
				synchronized (CreateMenuItemController.class) {
					createMenuItemController = new CreateMenuItemController();
				}
			}
		}
		return createMenuItemController;
	}
	public List<Side> getAllSides() {
		return orderPersistImp.getSides();
	}
	public List<Topping> getAllToppings() {
		return orderPersistImp.getToppings();
	}
	public List<CustomizableItem> getAllCustomizableItems() {
		return orderPersistImp.getCustomizableItems();
	}
	public Side getSideById(int id) {
		return orderPersistImp.getSideById(id);
	}
	public Topping getToppingById(int id) {
		return orderPersistImp.getToppingById(id);
	}
	public CustomizableItem getCustomizableItemById(int id) {
		return orderPersistImp.getCustomizableItemById(id);
	}
	public int deleteTopping(Topping topping) {
		return orderPersistImp.deleteTopping(topping);
	}
	public int deleteCustomizableItem(CustomizableItem customizableItem) {
		return orderPersistImp.deleteCustomizableItem(customizableItem);
	}
	public int deleteSide(Side side) {
		return orderPersistImp.deleteSide(side);
	}
	public MenuItem getMenuItemById(int id) {
		return orderPersistImp.getMenuItemById(id);
	}
	public List<MenuItem> getAllMenuItems() {
		return orderPersistImp.getAllMenuItems();
	}
	public int deleteMenuItem(MenuItem menuitem) {
		return orderPersistImp.deleteMenuItem(menuitem);
	}
	public int createSide(Side side) {
		return orderPersistImp.createSide(side);
	}
	public int updateSide(Side side) {
		return orderPersistImp.updateSide(side);
	}
	public MenuCategory getCategoryById(int id) {
		return orderPersistImp.getCategoryById(id);
	}
	public int deleteCategory(MenuCategory menuCategory) {
		return orderPersistImp.deleteCatagory(menuCategory);
	}
	public int createTopping(Topping topping) {
		return orderPersistImp.createTopping(topping);
	}
	public int updateTopping(Topping topping) {
		return orderPersistImp.updateTopping(topping);
	}

}
