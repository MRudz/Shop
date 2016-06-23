package by.epam.shop.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.resource.MessageManager;

public class UnknownCommand implements ICommand {
	private final static String MESSAGE = "message";
	@Override
	public String execute(HttpServletRequest request)  {
		String page = null;
		page = PageName.ERROR_PAGE;
		request.setAttribute(MESSAGE, MessageManager.UNKNOWN_COMMAND);
		return page;
	}

}
