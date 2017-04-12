package edu.uga.cs4300.persistlayer;

public class OrderPersistImp {

	DbAccessImpl dbAccessImpl = DbAccessImpl.getInstance();

	private static OrderPersistImp orderPersistImp;

	private OrderPersistImp() {

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
