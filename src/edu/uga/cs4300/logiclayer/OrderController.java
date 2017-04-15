package edu.uga.cs4300.logiclayer;

import java.util.List;

import edu.uga.cs4300.objectlayer.MenuItem;
import edu.uga.cs4300.persistlayer.OrderPersistImp;

public class OrderController {

	private OrderPersistImp orderPersistImp = OrderPersistImp.getInstance();

	private static OrderController orderController;

	private OrderController() {

	}

	public List<MenuItem> getMenuItemsForCatagory(int catagoryId) {
		return orderPersistImp.getMenuItemsForCatagory(catagoryId);
	}

	// method to return single instance of this class
	public static OrderController getInstance() {
		if (orderController != null) {
			return orderController;
		}
		synchronized (OrderController.class) {
			if (orderController == null) {
				synchronized (CreateMenuItemController.class) {
					orderController = new OrderController();
				}
			}
		}
		return orderController;
	}

}
