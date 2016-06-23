package by.epam.shop.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;

public class ChangeLocalCommand implements ICommand {
	private final static String URL = "url";
	private final static String LOCAL = "local";

	@Override
	public String execute(HttpServletRequest request)  {
		String page;
		request.getSession(true).setAttribute(LOCAL, request.getParameter(LOCAL));
        page = (String)request.getSession().getAttribute(URL);
		return page;
	}

	
}
