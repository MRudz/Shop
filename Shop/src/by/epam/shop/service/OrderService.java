package by.epam.shop.service;

import java.util.List;

import by.epam.shop.entity.Order;
import by.epam.shop.service.exception.ServiceException;

public interface OrderService {

	void createOrder(Order order) throws ServiceException;

	List<Order> getUserOrders(String login) throws ServiceException;
	
	boolean checkProduct(int id, String login) throws ServiceException;
}
