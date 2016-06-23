package by.epam.shop.command.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

public class ShowCatalogCommand implements ICommand {
	private final static String MESSAGE = "message";
	private final static String URL = "url";
	private final static String PRODUCTS = "products";
	private final static String GENRE = "genre";
	private final static String BEST_SELLERS = "bestSellers";
	private final static Logger LOGGER = LogManager.getRootLogger();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		double price = 0;
		List<Product> bestSellers = null;
		ProductService productService = ServiceFactory.getInstance().getProductService();
		String genre = request.getParameter(GENRE);
		try {
			List<Product> products = productService.getAllProducts(genre);
			if (!products.isEmpty()) {
				for (Product p : products) {
					if (p.isSale() == true) {
						price = p.getPrice() * 0.8;
						BigDecimal bd = new BigDecimal(price);
						bd = bd.setScale(2, RoundingMode.HALF_UP);
						p.setSalePrice(bd.doubleValue());
					}
				}
				bestSellers = productService.getBestSellers();
				request.getSession().setAttribute(BEST_SELLERS, bestSellers);
				request.getSession().setAttribute(PRODUCTS, products);
				page = PageName.MAIN_PAGE;
				request.getSession().setAttribute(URL, PageName.INDEX_PAGE);
			} else {
				page = PageName.MAIN_PAGE;
				request.setAttribute(MESSAGE, MessageManager.NOTHING_FOUND);
			}
		} catch (ServiceException e) {
			LOGGER.error(e);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
