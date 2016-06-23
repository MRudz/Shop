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

public class DeleteUserCommand implements ICommand {
	private final static String LOGIN = "login";
	private final static Logger LOGGER = LogManager.getRootLogger();
	private final static String MESSAGE = "message";
	private final static String USERS = "users";
	private final static String URL = "url";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<User> users = null;
		UserService userService = ServiceFactory.getInstance().getUserService();
		String login = request.getParameter(LOGIN);
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		try {
			userService.deleteUser(login);
			users = userService.allUsers();
			request.getSession().setAttribute(USERS, users);
			page = PageName.ADMIN_PAGE;
			request.getSession().setAttribute(URL, page);
		} catch (ServiceException e) {
			LOGGER.error(e);
			page = PageName.ERROR_PAGE;
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
		}
		return page;
	}

}
