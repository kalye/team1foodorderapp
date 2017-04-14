package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.MenuCategory;
import edu.uga.cs4300.objectlayer.MenuItem;

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

	public List<MenuItem> getMenuItemsForCatagory(int catagoryId){
		Connection connection = dbAccessImpl.connect();
		String query = "select * from menu_item where category_id = " + catagoryId + "" ;
		List<MenuItem> catagories = new ArrayList<>();
		ResultSet resultSet = dbAccessImpl.retrieve(connection, query);
		if (resultSet != null) {
			try {
				// loop through resultSet and get movie entity and add it to the
				// list
				while (resultSet.next()) {
					MenuItem menuItem = getEntity(resultSet, MenuItem.class);
					if(menuItem.isCustomizable()){
						menuItem.setCustomizableItem(getCustomizableItemForMenuItem(menuItem));
					}
					catagories.add(menuItem);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return catagories;
		
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
		String query = "insert into Category (name, imageUrl) values('" + category.getName() + "','" + category.getImageUrl() + "'";
		int id = dbAccessImpl.create(connection, query, true);
		category.setId(id);
		dbAccessImpl.disconnect(connection);
		return id;
	}
	private <T> T getEntity(ResultSet resultSet, Class<T> clazz) throws SQLException {
		if(clazz == null){
			return null;
		}
		if(clazz.equals(MenuCategory.class)){
			return (T) new MenuCategory().update(resultSet);
		} else if(clazz.equals(MenuItem.class)){
			return (T) new MenuItem().update(resultSet);
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
}
