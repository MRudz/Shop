package by.epam.shop.command.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.OrderService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class AddToBasketCommand implements ICommand {
	private static final String ID = "id";
	private static final String PRODUCTS = "products";
	private static final String BASKET = "basket";
	private static final String MESSAGE = "message";
	private static final String LOGIN = "login";
	private static final Logger LOGGER = LogManager.getRootLogger();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Product> basket = null;
		boolean result = false;
		int id = Integer.parseInt(request.getParameter(ID));
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		String login = user.getLogin();
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		try {
			result = orderService.checkProduct(id, login);
		} catch (ServiceException e) {
			LOGGER.error(e);
			page = PageName.ERROR_PAGE;
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
		}
		if (result == false) {
			basket = (List<Product>) request.getSession().getAttribute(BASKET);
			if (basket == null) {
				basket = new ArrayList<Product>();
			}
			List<Product> products = (List<Product>) request.getSession().getAttribute(PRODUCTS);
			for (Product p : products) {
				if (basket.contains(p) && id == p.getId()) {
					request.setAttribute(MESSAGE, MessageManager.PRODUCT_IN_CART);
					break;
				} else if (id == p.getId()) {
					if (p.isSale()) {
						p.setPrice(p.getSalePrice());
					}

					basket.add(p);
					request.setAttribute(MESSAGE, MessageManager.ADD_TO_CART);

				}

			}
			request.getSession().setAttribute(BASKET, basket);

		} else {
			request.setAttribute(MESSAGE, MessageManager.REPEAT_ORDER);
		}
		page = PageName.MAIN_PAGE;
		return page;
	}

}
