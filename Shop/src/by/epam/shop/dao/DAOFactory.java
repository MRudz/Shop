package by.epam.shop.dao;

import by.epam.shop.dao.impl.OrderDaoImpl;
import by.epam.shop.dao.impl.ProductDaoImpl;
import by.epam.shop.dao.impl.UserDaoImpl;

public class DAOFactory {
	private static final DAOFactory INSTANCE = new DAOFactory();
	private static final UserDao USER_DAO = new UserDaoImpl();
	private static final ProductDao PRODUCT_DAO = new ProductDaoImpl();
	private static final OrderDao ORDER_DAO = new OrderDaoImpl();

	public static DAOFactory getInstance() {
		return INSTANCE;
	}
	
	public UserDao getUserDao(){
		return USER_DAO;
	}
	
	public ProductDao getProductDao(){
		return PRODUCT_DAO;
	}
	
	public OrderDao getOrderDao(){
		return ORDER_DAO;
	}
}
