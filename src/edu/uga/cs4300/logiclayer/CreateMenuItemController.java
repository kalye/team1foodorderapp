package edu.uga.cs4300.logiclayer;

import java.util.List;

import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.MenuCategory;
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

}
