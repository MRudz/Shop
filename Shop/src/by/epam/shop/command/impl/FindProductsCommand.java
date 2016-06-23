package by.epam.shop.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class FindProductsCommand implements ICommand {
	private static final String PRODUCTS = "products";
	private static final String URL = "url";
	private static final String NAME = "name";
	private static final String MESSAGE = "message";
	private static final Logger LOGGER = LogManager.getRootLogger();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String name = null;
		List<Product> products = null;
		name = request.getParameter(NAME);
		ProductService productService = ServiceFactory.getInstance().getProductService();
		if (name != null) {
			try {
				
				products = productService.findProducts(name);
				request.getSession().setAttribute(PRODUCTS, products);

				page = PageName.MAIN_PAGE;
				request.getSession().setAttribute(URL, page);
			} catch (ServiceException e) {
				LOGGER.error(e);
				request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
				page = PageName.ERROR_PAGE;
			}

		} else {
			page = (String) request.getSession().getAttribute(URL);
			request.setAttribute(MESSAGE, MessageManager.EMPTY_FIELD);
		}
		return page;
	}

}
