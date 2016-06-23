package by.epam.shop.service;


import by.epam.shop.service.impl.OrderServiceImpl;
import by.epam.shop.service.impl.ProductServiceImpl;
import by.epam.shop.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory INSTANCE = new ServiceFactory();
	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl();
	private static final OrderService ORDER_SERVICE = new OrderServiceImpl();

	public static ServiceFactory getInstance() {
		return INSTANCE;
	}

	public UserService getUserService() {
		return USER_SERVICE;
	}

	public ProductService getProductService() {
		return PRODUCT_SERVICE;
	}

	public OrderService getOrderService() {
		return ORDER_SERVICE;
	}

}
