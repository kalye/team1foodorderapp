package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import edu.uga.cs4300.objectlayer.MenuCategory;

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
					//loop through resultSet and get movie entity and add it to the list
					while (resultSet.next()) {
						catagories.add(getEntity(resultSet, null));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return catagories;
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
	private <T> T getEntity(ResultSet resultSet, Class<T> clazz) throws SQLException {
		if(clazz == null){
			return null;
		}
		if(clazz.equals(MenuCategory.class)){
			return (T) new MenuCategory().update(resultSet);
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
