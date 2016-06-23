package by.epam.shop.command.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;

public class SortProductsCommand implements ICommand {
	private static final String TYPE = "type";
	private static final String PRODUCTS = "products";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String type = request.getParameter(TYPE);
		Type t = Type.valueOf(type.toUpperCase());
		List<Product> products = (List<Product>) request.getSession().getAttribute(PRODUCTS);
		if (products != null && !(products.isEmpty())){
		switch (t) {
		case NAME:
			Collections.sort(products, new SortByName());
			break;
		case PRICE:
			Collections.sort(products, new SortByPrice());
			break;
		case AUTHOR:
			Collections.sort(products, new SortByAuthor());
			break;
		default:
		}
		request.getSession().setAttribute(PRODUCTS, products);
		}
		page = PageName.MAIN_PAGE;
		return page;
	}

}

enum Type {
	NAME, PRICE, AUTHOR
}

class SortByName implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getName().compareTo(o2.getName());
	}

}

class SortByAuthor implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getName().compareTo(o2.getAuthor());
	}

}

class SortByPrice implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		if (o1.getPrice() > o2.getPrice()) {
			return 1;
		} else if (o1.getPrice() < o2.getPrice()) {
			return -1;
		}
		return 0;
	}

}
