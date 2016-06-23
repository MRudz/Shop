package by.epam.shop.service.impl;

import java.util.List;

import by.epam.shop.dao.DAOFactory;
import by.epam.shop.dao.OrderDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.Order;
import by.epam.shop.entity.Product;
import by.epam.shop.service.OrderService;
import by.epam.shop.service.exception.ServiceException;

public class OrderServiceImpl implements OrderService {

	@Override
	public void createOrder(Order order) throws ServiceException {
		OrderDao orderDao = DAOFactory.getInstance().getOrderDao();
		try {
			orderDao.createOrder(order);
			orderDao.addProductsToOrder(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> getUserOrders(String login) throws ServiceException {
		List<Order> orders = null;
		List<Product> products = null;
		OrderDao orderDao = DAOFactory.getInstance().getOrderDao();
		try {
			orders = orderDao.findUserOrders(login);
			for (Order order : orders) {
				products = orderDao.productsToOrder(order);
				order.setProductsList(products);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orders;
	}

	@Override
	public boolean checkProduct(int id, String login) throws ServiceException {
		boolean result = false;
		OrderDao orderDao = DAOFactory.getInstance().getOrderDao();
		try {
			result = orderDao.checkProduct(id,login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

}
