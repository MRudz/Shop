package by.epam.shop.command.impl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;

public class DeleteFromBasketCommand implements ICommand {
	private static final String ID = "id";
	private static final String BASKET = "basket";
	private static final String MESSAGE = "message";
	private static final String LOGIN = "login";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Product> basket;
		int id = Integer.parseInt(request.getParameter(ID));
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		basket = (List<Product>) request.getSession().getAttribute(BASKET);
		Iterator<Product> iter = basket.iterator();
		while (iter.hasNext()) {
			if (iter.next().getId() == id) {
				iter.remove();
				page = PageName.BASKET_PAGE;
			}
		}
		request.getSession().setAttribute(BASKET, basket);
		return page;
	}

}
