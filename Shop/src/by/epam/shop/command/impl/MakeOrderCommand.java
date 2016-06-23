package by.epam.shop.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Order;
import by.epam.shop.entity.Product;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.OrderService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class MakeOrderCommand implements ICommand {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String BASKET = "basket";
	private static final String LOGIN = "login";
	private static final String MESSAGE = "message";
	private static final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		User user = null;
		double sum = 0;
		List<Product> basket = null;
		basket = (List<Product>) request.getSession().getAttribute(BASKET);
		user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		try {
			if (basket != null) {
				Order order = new Order();
				order.setUserLogin(user.getLogin());
				for (Product p : basket) {
					sum += p.getPrice();
				}
				order.setProductsList(basket);
				order.setSumPrice(sum);
				orderService.createOrder(order);
				request.getSession().setAttribute(BASKET, null);

				request.setAttribute(MESSAGE, MessageManager.MAKE_ORDER_SUCCESS);

			}
			page = PageName.BASKET_PAGE;
			request.setAttribute(URL, page);
		} catch (ServiceException e) {
			page = PageName.ERROR_PAGE;
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
		}
		return page;
	}

}
