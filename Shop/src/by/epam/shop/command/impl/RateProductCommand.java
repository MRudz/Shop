package by.epam.shop.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class RateProductCommand implements ICommand {
	private final static String ID = "id";
	private final static String RATE = "rate";
	private final static String LOGIN = "login";
	private final static String MESSAGE = "message";
	private final static Logger LOGGER = LogManager.getRootLogger();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		User user = null;
		int id = Integer.parseInt(request.getParameter(ID));
		int rate = Integer.parseInt(request.getParameter(RATE));
		user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			page = PageName.REGISTER_PAGE;
			return page;
		}
		String login = user.getLogin();
		ProductService productService = ServiceFactory.getInstance().getProductService();
		try {
			productService.rateProduct(login,id,rate);
			page = PageName.INDEX_PAGE;
		} catch (ServiceException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
