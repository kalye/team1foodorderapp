package edu.uga.cs4300.logiclayer;

import java.util.List;

import edu.uga.cs4300.objectlayer.MenuCategory;
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

}
