package by.epam.shop.command.impl.admin;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class ChangeSaleCommand implements ICommand {
	private static final String ID = "id";
	private static final String MESSAGE = "message";
	private static final String SALE = "sale";
	private static final String LOGIN = "login";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		ProductService productService = null;
		int id = Integer.parseInt(request.getParameter(ID));
		boolean sale = Boolean.parseBoolean(request.getParameter(SALE));
		productService = ServiceFactory.getInstance().getProductService();
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		try {
			productService.changeSale(id, sale);
			page = PageName.INDEX_PAGE;
		} catch (ServiceException e) {
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
