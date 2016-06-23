package by.epam.shop.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Order;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.OrderService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class AllUserOrdersCommand implements ICommand {
	private static final String LOGIN = "login";
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String USER_ORDERS = "userOrders";
	private static final String MESSAGE = "message";
	private static final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Order> orders = null;

		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		User user = (User)request.getSession().getAttribute(LOGIN);
		
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		String login = user.getLogin();
		try {
			orders = orderService.getUserOrders(login);
			if (orders.isEmpty()) {
				page = PageName.ORDERS_PAGE;
				request.setAttribute(MESSAGE, MessageManager.NO_ORDERS);
			} else {
				request.getSession().setAttribute(USER_ORDERS, orders);
				page = PageName.ORDERS_PAGE;
				request.getSession().setAttribute(URL, page);
			}
		} catch (ServiceException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
