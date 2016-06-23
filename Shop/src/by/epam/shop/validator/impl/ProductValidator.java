package by.epam.shop.validator.impl;

import java.util.regex.Pattern;

import by.epam.shop.entity.Product;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.validator.Validator;

public class ProductValidator implements Validator<Product> {
	private static final Pattern PRICE_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

	@Override
	public String isValid(Product product) {
		String result = null;
		String price = String.valueOf(product.getPrice());
		if (!PRICE_PATTERN.matcher(price).matches()) {
			result = MessageManager.INCORRECT_PRICE;
			return result;
		}

		return result;
	}

}
