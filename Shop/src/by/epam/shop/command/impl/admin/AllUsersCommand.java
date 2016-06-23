package by.epam.shop.command.impl.admin;

import java.util.List;

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

public class AllUsersCommand implements ICommand {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String MESSAGE = "message";
	private static final String USERS = "users";
	private static final String LOGIN = "login";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<User> users = null;
		UserService userService = ServiceFactory.getInstance().getUserService();
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		try {
			users = userService.allUsers();
			if (!users.isEmpty()) {
				request.getSession().setAttribute(USERS, users);
			} else {
				request.setAttribute(MESSAGE, MessageManager.NO_USERS);
			}
			page = PageName.ADMIN_PAGE;
		} catch (ServiceException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
