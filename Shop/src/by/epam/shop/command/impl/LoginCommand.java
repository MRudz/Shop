package by.epam.shop.command.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.UserService;
import by.epam.shop.service.exception.ServiceException;

public class LoginCommand implements ICommand {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String MESSAGE = "message";
	private static final String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		UserService userService = ServiceFactory.getInstance().getUserService();
		User user = null;
		try {
			user = userService.signIn(request.getParameter(LOGIN), request.getParameter(PASSWORD));
			if (user != null) {
				if (!user.isBlackList()) {
					request.getSession(true).setAttribute(LOGIN, user);
					page = (String) request.getSession().getAttribute(URL);
				} else {
					request.setAttribute(MESSAGE, MessageManager.BLACKLIST);
					page = PageName.REGISTER_PAGE;
				}
			} else {
				request.setAttribute(MESSAGE, MessageManager.LOGIN_ERROR);
				page = PageName.REGISTER_PAGE;
			}
		} catch (ServiceException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}

		return page;
	}

}
