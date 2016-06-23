package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;

public class ToAdminCommand implements ICommand {
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		page = PageName.ADMIN_PAGE;
		return page;
	}

}
