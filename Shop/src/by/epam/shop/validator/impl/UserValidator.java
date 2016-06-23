package by.epam.shop.validator.impl;

import java.util.regex.Pattern;

import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.validator.Validator;

public class UserValidator implements Validator<User> {
	private static final Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{3,16}$");
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("\\w{5,25}");
	private static final Pattern NAME_SNAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^375[0-9]{9}$");
	private static final Pattern MAIL_PATTERN = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public String isValid(User user) {
		if (!LOGIN_PATTERN.matcher(user.getLogin()).matches())
			return MessageManager.INCORRECT_LOGIN;

		if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches())
			return MessageManager.INCORRECT_PASSWORD;

		if (!NAME_SNAME_PATTERN.matcher(user.getName()).matches()
				&& !NAME_SNAME_PATTERN.matcher(user.getSname()).matches())
			return MessageManager.INCORRECT_NAME;
		if (!PHONE_PATTERN.matcher(user.getPhone()).matches())
			return MessageManager.INCORRECT_PHONE;
		if (!MAIL_PATTERN.matcher(user.getEmail()).matches()) {
			return MessageManager.INCORRECT_MAIL;
		}

		return null;
	}
}
