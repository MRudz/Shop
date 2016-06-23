package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;

public class ToMainCommand implements ICommand {
	private static final String URL = "url";
	@Override
	public String execute(HttpServletRequest request) {
		String page = PageName.INDEX_PAGE;
		request.getSession().setAttribute(URL, page);
		return page;
	}

}
