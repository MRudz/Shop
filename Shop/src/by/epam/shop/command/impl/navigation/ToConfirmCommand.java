package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;

public class ToConfirmCommand implements ICommand {
	private static final String URL = "url";
	private static final String MESSAGE = "message";
	private static final String LOGIN = "login";

	@Override
	public String execute(HttpServletRequest request) {
		String page = PageName.CONFIRM_PAGE;
		User user = (User) request.getSession().getAttribute(LOGIN);
		request.getSession().setAttribute(URL, page);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
		}
		return page;
	}

}
