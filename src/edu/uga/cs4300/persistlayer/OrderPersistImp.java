package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.MenuItem;
import edu.uga.cs4300.objectlayer.Side;
import edu.uga.cs4300.objectlayer.Topping;

public class OrderPersistImp {

	DbAccessImpl dbAccessImpl = DbAccessImpl.getInstance();
	
	public static final String CATAGORIES_ALL = "select * from Category;";

	private static OrderPersistImp orderPersistImp;

	private OrderPersistImp() {

	}
	
	public List<MenuCategory> getAllCatagories(){
		String query = CATAGORIES_ALL;
		List<MenuCategory> catagories = new ArrayList<>();
		Connection connection = dbAccessImpl.connect();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		//convert resultSet to list of movies
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					catagories.add(getEntity(resultSet, MenuCategory.class));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return catagories;
	}
	
	public int createMenuItem(MenuItem menuItem){
		if(menuItem == null){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "insert into menu_item (category_id, name, description, hasToppings, hasSide, customizable, price, imageUrl) values("
					    + menuItem.getCategoryId() + ",'" + menuItem.getName() + "','"+ menuItem.getDescription() 
					    + "', " + menuItem.isHasToppings() + "," + menuItem.isHasSide() +", " + menuItem.isCustomizable() + ", '"
					    		+ menuItem.getPrice().toPlainString() +  "', '" + menuItem.getImageUrl() + "');";
		int id = dbAccessImpl.create(connection, query, true);
		menuItem.setId(id);
		if(id != 0 ){
			if(menuItem.isHasToppings()){
				for(Topping topping: menuItem.getToppings()){
					query = "insert into menu_toppings (menu_id, topping_id) values(" + menuItem.getId() + ", "
							+ topping.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
			if(menuItem.isHasSide()){
				for(Side side: menuItem.getSides()){
					query = "insert into sides_menu (menu_id, side_id) values(" + menuItem.getId() + ", "
							+ side.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
			if(menuItem.isCustomizable()){
				for(CustomizableItem customizableItem: menuItem.getCustomizableItems()){
					query = "insert into menu_customizable_item (menu_id, c_item_id) values(" + menuItem.getId() + ", "
							+ customizableItem.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
		}
		dbAccessImpl.disconnect(connection);
		return id;
	}
	public int updateMenuItem(MenuItem menuItem, List<Topping> toppingsToBeAddToMenu,List<Topping> toppingsTobeRemovedFromMenu,
			List<Side> sidesToBeAddedToMenu, List<Side> sidesToBeRemovedFromMenu,
			List<CustomizableItem> customizableItemToBeAddedToMenu, List<CustomizableItem> customizableItemToBeRemovedFromMenu){
		if(menuItem == null || menuItem.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "update menu_item set category_id = "  + menuItem.getCategoryId() + ", name = '" + menuItem.getName() + "', description = '"
				+ menuItem.getDescription() + "',  hasToppings = " + menuItem.isHasToppings() + ", hasSide = "+ menuItem.isHasSide() + ", customizable = " + menuItem.isCustomizable() + ""
						+ ", price = '" + menuItem.getPrice().toPlainString() +  "', imageUrl = '"+ menuItem.getImageUrl() + "' ";
		int row = dbAccessImpl.create(connection, query);
		
		if(row != 0 ){
			if(toppingsToBeAddToMenu != null){
				for(Topping topping: toppingsToBeAddToMenu){
					query = "insert into menu_toppings (menu_id, topping_id) values(" + menuItem.getId() + ", "
							+ topping.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
			if (toppingsTobeRemovedFromMenu != null) {
				for(Topping topping: toppingsTobeRemovedFromMenu){
					query = "delete from menu_toppings where menu_id = "+ menuItem.getId() + " and topping_id = "
							+ topping.getId() + ";";
					dbAccessImpl.delete(connection, query);
				}
			}
			if (sidesToBeAddedToMenu != null) {
				for(Side side: sidesToBeAddedToMenu){
					query = "insert into sides_menu (menu_id, side_id) values(" + menuItem.getId() + ", "
							+ side.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
			if (sidesToBeRemovedFromMenu != null) {
				for(Side side: sidesToBeRemovedFromMenu){
					query = "delete from sides_menu where menu_id = " + menuItem.getId() + " and side_id = "
							+ side.getId() + ";";
					dbAccessImpl.delete(connection, query);
				}
			}
			if (customizableItemToBeAddedToMenu != null) {
				for(CustomizableItem customizableItem: customizableItemToBeAddedToMenu){
					query = "insert into menu_customizable_item (menu_id, c_item_id) values(" + menuItem.getId() + ", "
							+ customizableItem.getId() + ");";
					dbAccessImpl.create(connection, query, true);
				}
			}
			if (customizableItemToBeRemovedFromMenu != null) {
				for(CustomizableItem customizableItem: customizableItemToBeRemovedFromMenu){
					query = "delete from menu_customizable_item where menu_id = " + menuItem.getId() + " and c_item_id = "  
							+ customizableItem.getId() + ";";
					dbAccessImpl.create(connection, query, true);
				}
			}
		}
		
		return row;
	}
	public List<MenuItem> getMenuItemsForCatagory(int catagoryId){
		Connection connection = dbAccessImpl.connect();
		String query = "select * from menu_item where category_id = " + catagoryId + "" ;
		List<MenuItem> menuitems = new ArrayList<>();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					MenuItem menuItem = getEntity(resultSet, MenuItem.class);
					if(menuItem.isCustomizable()){
						menuItem.setCustomizableItems(getCustomizableItemForMenuItem(menuItem));
					}
					if(menuItem.isHasSide()){
						addSidesToMenuItem(menuItem);
					}
					if(menuItem.isHasToppings()){
						addToppingsToMenuItem(menuItem);
					}
					menuitems.add(menuItem);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuitems;
		
	}
	public int deleteMenuItem(MenuItem menuItem){
		if(menuItem == null || menuItem.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "delete from menu_item where id = " + menuItem.getId() + ";";
		int row = dbAccessImpl.delete(connection, query);
		if(row != 0){
			//we used try catch for deleting relationship just in case it's already deleted from table definition cascading
			try{
				if (menuItem.isCustomizable()) {
					for(CustomizableItem customizableItem: menuItem.getCustomizableItems()){
						query = "delete from menu_customizable_item where menu_id = " + menuItem.getId() + " and c_item_id = "  
								+ customizableItem.getId() + ";";
						dbAccessImpl.create(connection, query, true);
					}
				}
				if (menuItem.isHasSide()) {
					for(Side side: menuItem.getSides()){
						query = "delete from sides_menu where menu_id = " + menuItem.getId() + " and side_id = "
								+ side.getId() + ";";
						dbAccessImpl.delete(connection, query);
					}
				}
				if (menuItem.isHasToppings()) {
					for (Topping topping : menuItem.getToppings()) {
						query = "delete from menu_toppings where menu_id = " + menuItem.getId() + " and topping_id = "
								+ topping.getId() + ";";
						dbAccessImpl.delete(connection, query);
					}
				}
			} catch(Exception e){
				System.out.println("Exception happened while deleting relationship with menu item. Exception: " + e.getMessage());
			}
		}
		return row;
	}
	public int deleteSide(Side side){
		if(side == null || side.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "delete from sides where id = " + side.getId() + ";";
		int row = dbAccessImpl.delete(connection, query);
		if(row != 0){
			try{
				query = "delete from sides_menu where side_id = " + side.getId() + ";";
				dbAccessImpl.delete(connection, query);
			} catch(Exception e){
				System.out.println("Exception happened while deleting relationship with side. Exception: " + e.getMessage());
			}
		}
		return row;
	}
	public int deleteTopping(Topping topping){
		if(topping == null || topping.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "delete from toppings where id = " + topping.getId() + ";";
		int row = dbAccessImpl.delete(connection, query);
		if(row != 0){
			try{
				query = "delete from menu_toppings where topping_id = " + topping.getId() + ";";
				dbAccessImpl.delete(connection, query);
			} catch(Exception e){
				System.out.println("Exception happened while deleting relationship with topping. Exception: " + e.getMessage());
			}
		}
		return row;
	}
	
	public int deleteCatagory(MenuCategory catagory){
		if(catagory == null || catagory.getId() == 0){
			return 0;
		}
		//first delete all menu item that belong to this category
		List<MenuItem> menuItems = getMenuItemsForCatagory(catagory.getId());
		if(menuItems != null){
			for(MenuItem menuItem: menuItems){
				try{
					deleteMenuItem(menuItem);
				} catch(Exception e){
					System.out.println("Exception happened while deleting relationship with catagory-menuItem. Exception: " + e.getMessage());
				}
			}
		}
		Connection connection = dbAccessImpl.connect();
		String query = "delete from toppings where id = " + catagory.getId() + ";";
		int row = dbAccessImpl.delete(connection, query);
		return row;
	}
	public int deleteCustomizableItem(CustomizableItem customizableItem){
		if(customizableItem == null || customizableItem.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "delete from customizable_item where id = " + customizableItem.getId() + ";";
		int row = dbAccessImpl.delete(connection, query);
		if(row != 0){
			try{
				query = "delete from menu_customizable_item where c_item_id = " + customizableItem.getId() + ";";
				dbAccessImpl.delete(connection, query);
			} catch(Exception e){
				System.out.println("Exception happened while deleting relationship with customizableItem. Exception: " + e.getMessage());
			}
		}
		return row;
	}
	public void addToppingsToMenuItem(MenuItem menuItem) {
		if(menuItem != null && menuItem.getId() != 0){
			List<Topping> toppings = new ArrayList<>();

			Connection connection = dbAccessImpl.connect();
			String query = "select * from toppings tp inner join menu_toppings mt on tp.id = mt.topping_id " +
			"inner join menu_item mi mi.id = mt.menu_id and mi.id = " + menuItem.getId() + "";
			ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
			if (resultSet != null) {
				try {
					// loop through resultSet and get movie entity and add it to the
					// list
					while (resultSet.next()) {
						Topping topping = getEntity(resultSet, Topping.class);
						toppings.add(topping);
					}
					menuItem.setToppings(toppings);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		
		}
	}
	
	public List<Topping> getToppings() {
		List<Topping> toppings = new ArrayList<>();
		Connection connection = dbAccessImpl.connect();
		String query = "select * from toppings;";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					Topping topping = getEntity(resultSet, Topping.class);
					toppings.add(topping);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return toppings;
	}

	public void addSidesToMenuItem(MenuItem menuItem) {
		if(menuItem != null && menuItem.getId() != 0){
			List<Side> sides = new ArrayList<>();

			Connection connection = dbAccessImpl.connect();
			String query = "select * from sides si inner join sides_menu sm on si.id = sm.side_id " +
			"inner join menu_item mi mi.id = sm.menu_id and mi.id = " + menuItem.getId() + "";
			ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
			if (resultSet != null) {
				try {
					// loop through resultSet and get movie entity and add it to the
					// list
					while (resultSet.next()) {
						Side side = getEntity(resultSet, Side.class);
						sides.add(side);
					}
					menuItem.setSides(sides);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		}
	}

	public List<Side> getSides(){
		List<Side> sides = new ArrayList<>();
		Connection connection = dbAccessImpl.connect();
		String query = "select * from sides;";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					Side side = getEntity(resultSet, Side.class);
					sides.add(side);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sides;
	}
	public List<CustomizableItem> getCustomizableItems(){
		List<CustomizableItem> customizableItems = new ArrayList<>();
		Connection connection = dbAccessImpl.connect();
		String query = "select * from customizable_item";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					CustomizableItem customizableItem = getEntity(resultSet, CustomizableItem.class);
					customizableItems.add(customizableItem);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customizableItems;
	}
	public List<CustomizableItem> getCustomizableItemForMenuItem(MenuItem menuItem) {
		List<CustomizableItem> customizableItems = new ArrayList<>();
		if(menuItem != null && menuItem.getId() != 0){
			Connection connection = dbAccessImpl.connect();
			String query = "select * from customizable_item ci inner join menu_customizable_item mci on ci.id = mci.c_item_id " +
			"inner join menu_item mi mi.id = mci.menu_id and mi.id = " + menuItem.getId() + "";
			ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
			if (resultSet != null) {
				try {
					// loop through resultSet and get movie entity and add it to the
					// list
					while (resultSet.next()) {
						CustomizableItem customizableItem = getEntity(resultSet, CustomizableItem.class);
						customizableItems.add(customizableItem);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return customizableItems;
	}

	public int createCustomizableItem(CustomizableItem customizableItem){
		if(customizableItem == null){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "insert into customizable_item (name, imageUrl, price) values('" + customizableItem.getName() + "','" + customizableItem.getImageUrl() + "','"
				+ customizableItem.getPrice().toPlainString() + "');";
		int id = dbAccessImpl.create(connection, query, true);
		customizableItem.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	
	public int updateCustomizableItem(CustomizableItem customizableItem){
		if(customizableItem == null || customizableItem.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "update customizable_item set name = '"  + customizableItem.getName() + "',"
				+ " imageUrl = '" + customizableItem.getImageUrl() + "'," 
				+ " price = '" + customizableItem.getPrice().toPlainString() + "' where id = " + customizableItem.getId() + ";";
		int rows = dbAccessImpl.create(connection, query);
		dbAccessImpl.disconnect(connection);
		return rows;
	}
	public int createTopping(Topping topping){
		if(topping == null){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "insert into toppings (name, imageUrl, price) values('" + topping.getName() + "','" + topping.getImageUrl() + "','"
				+ topping.getPrice().toPlainString() + "');";
		int id = dbAccessImpl.create(connection, query, true);
		topping.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	public int updateTopping(Topping topping){
		if(topping == null || topping.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "update toppings set name = '" + topping.getName() + "', imageUrl = '"+ topping.getImageUrl()
					+ "', price = '"+ topping.getPrice().toPlainString() + "' where id = "  + topping.getId() + "";
		int row = dbAccessImpl.create(connection, query, true);
		dbAccessImpl.disconnect(connection);
		return row;
	}
	public int createSide(Side side){
		if(side == null){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "insert into sides (name, imageUrl, price) values('" + side.getName() + "','" + side.getImageUrl() + "','"
				+ side.getPrice().toPlainString() + "');";
		int id = dbAccessImpl.create(connection, query, true);
		side.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	public int updateSide(Side side){
		if(side == null || side.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "update sides set name = '" + side.getName() + "', imageUrl = '" + side.getImageUrl() + "', price = '"
				+ side.getPrice().toPlainString() + "' where id = " + side.getId() + ";";
		int id = dbAccessImpl.create(connection, query, true);
		side.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	public int updateCategory(MenuCategory category){
		if(category == null || category.getId() == 0){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "update Category set name = '" + category.getName() + "', " + 
		"imageUrl = '" + category.getImageUrl() + "' where id = " + category.getId();
		int count = dbAccessImpl.update(connection, query);
		dbAccessImpl.disconnect(connection);
		return count;
	}
	public int createCategory(MenuCategory category){
		if(category == null){
			return 0;
		}
		Connection connection = dbAccessImpl.connect();
		String query = "insert into Category (name, imageUrl) values('" + category.getName() + "','" + category.getImageUrl() + "')";
		int id = dbAccessImpl.create(connection, query, true);
		category.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	@SuppressWarnings("unchecked")
	private <T> T getEntity(ResultSet resultSet, Class<T> clazz) throws SQLException {
		if(clazz == null){
			return null;
		}
		if(clazz.equals(MenuCategory.class)){
			return (T) new MenuCategory().update(resultSet);
		} else if(clazz.equals(MenuItem.class)){
			return (T) new MenuItem().update(resultSet);
		} else if(clazz.equals(Side.class)){
			return (T) new Side().update(resultSet);
		} else if(clazz.equals(Topping.class)){
			return (T) new Topping().update(resultSet);
		} else if(clazz.equals(CustomizableItem.class)){
			return (T) new CustomizableItem().update(resultSet);
		}
		return null;
	}

	// method to return single instance of this object
	public static OrderPersistImp getInstance() {
		if (orderPersistImp != null) {
			return orderPersistImp;
		}
		synchronized (OrderPersistImp.class) {
			if (orderPersistImp == null) {
				synchronized (OrderPersistImp.class) {
					orderPersistImp = new OrderPersistImp();
				}
			}
		}
		return orderPersistImp;
	}

	public Topping getToppingById(int id) {
		Connection connection = dbAccessImpl.connect();
		String query = "select * from toppings where id = " +  id + "";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		Topping topping = null;
		if (resultSet != null) {
			try {
				topping = getEntity(resultSet, Topping.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return topping;
	}
	public Side getSideById(int id) {
		Connection connection = dbAccessImpl.connect();
		String query = "select * from sides where id = " +  id + "";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		Side side = null;
		if (resultSet != null) {
			try {
				side = getEntity(resultSet, Side.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return side;
	}
	public CustomizableItem getCustomizableItemById(int id) {
		Connection connection = dbAccessImpl.connect();
		String query = "select * from customizable_item where id = " +  id + "";
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		CustomizableItem customizableItem = null;
		if (resultSet != null) {
			try {
				customizableItem = getEntity(resultSet, CustomizableItem.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customizableItem;
	}

	public MenuItem getMenuItemById(int id) {
		Connection connection = dbAccessImpl.connect();
		String query = "select * from menu_item where id = " + id + "" ;
		MenuItem menuitem = null;
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				MenuItem menuItem = getEntity(resultSet, MenuItem.class);
				if (menuItem.isCustomizable()) {
					menuItem.setCustomizableItems(getCustomizableItemForMenuItem(menuItem));
				}
				if (menuItem.isHasSide()) {
					addSidesToMenuItem(menuItem);
				}
				if (menuItem.isHasToppings()) {
					addToppingsToMenuItem(menuItem);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuitem;
	}

	public List<MenuItem> getAllMenuItems() {
		Connection connection = dbAccessImpl.connect();
		String query = "select * from menu_item;" ;
		List<MenuItem> menuitems = new ArrayList<>();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					MenuItem menuItem = getEntity(resultSet, MenuItem.class);
					if(menuItem.isCustomizable()){
						menuItem.setCustomizableItems(getCustomizableItemForMenuItem(menuItem));
					}
					if(menuItem.isHasSide()){
						addSidesToMenuItem(menuItem);
					}
					if(menuItem.isHasToppings()){
						addToppingsToMenuItem(menuItem);
					}
					menuitems.add(menuItem);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuitems;
	}

	public MenuCategory getCategoryById(int id) {
		String query = CATAGORIES_ALL + " where id = " + id + ";";
		MenuCategory menuCategory = null;
		Connection connection = dbAccessImpl.connect();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				menuCategory = getEntity(resultSet, MenuCategory.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menuCategory;
	}
}
