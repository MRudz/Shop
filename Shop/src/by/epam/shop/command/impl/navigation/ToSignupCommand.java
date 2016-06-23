package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;

public class ToSignupCommand implements ICommand{
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = PageName.REGISTER_PAGE;
		return page;
	}

}
