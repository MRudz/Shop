package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;

public class ToBasketCommand implements ICommand{
	private static final String URL = "url";
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		page = PageName.BASKET_PAGE;
		request.getSession().setAttribute(URL, page);
		return page;
	}

}
