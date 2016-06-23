package by.epam.shop.dao;

import java.util.List;

import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.Order;
import by.epam.shop.entity.Product;

public interface OrderDao {
	void createOrder(Order order) throws DAOException;

	void addProductsToOrder(Order order) throws DAOException;

	List<Order> findUserOrders(String login) throws DAOException;

	List<Product> productsToOrder(Order order) throws DAOException;
	
	boolean checkProduct(int id, String login) throws DAOException;
}
