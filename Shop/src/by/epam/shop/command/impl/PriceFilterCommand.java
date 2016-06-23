package by.epam.shop.command.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class PriceFilterCommand implements ICommand {
	private static final String PRODUCTS = "products";
	private static final String AMOUNT = "amount";
	private static final String MESSAGE = "message";
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final Pattern PRICE_PATTERN = Pattern.compile("\\$([0-9]+)\\s-\\s\\$([0-9]+)");

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		Product product = null;
		int minPrice = 0;
		int maxPrice = 30;
		List<Product> products = (List<Product>) request.getSession().getAttribute(PRODUCTS);
		String amount = request.getParameter(AMOUNT);
		Matcher priceMatcher = PRICE_PATTERN.matcher(amount);
		if (priceMatcher.find()) {
			minPrice = Integer.parseInt(priceMatcher.group(1));
			maxPrice = Integer.parseInt(priceMatcher.group(2));
		}
		if (products == null || products.isEmpty()) {
			ProductService productService = ServiceFactory.getInstance().getProductService();
			try {
				products = productService.getAllProducts(null);
				if (!products.isEmpty()) {
					for (Product p : products) {
						if (p.isSale() == true) {
							double price = p.getPrice() * 0.8;
							BigDecimal bd = new BigDecimal(price);
							bd = bd.setScale(2, RoundingMode.HALF_UP);
							p.setSalePrice(bd.doubleValue());
						}
					}
				}
			} catch (ServiceException e) {
				LOGGER.error(e);
				page = PageName.ERROR_PAGE;
				request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
				return page;
			}
		}
		Iterator<Product> iter = products.iterator();
		while (iter.hasNext()) {
			product = iter.next();
			if (product.getPrice() < minPrice || product.getPrice() > maxPrice)
				iter.remove();
		}
		request.getSession().setAttribute(PRODUCTS, products);
		page = PageName.MAIN_PAGE;
		return page;
	}

}
