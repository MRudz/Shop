package by.epam.shop.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;

public class LogoutCommand implements ICommand{

	@Override
	public String execute(HttpServletRequest request) {
		String page = PageName.INDEX_PAGE;
		request.getSession().invalidate();
		return page;
	}

}
