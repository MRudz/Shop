package by.epam.shop.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.resource.PasswordCoding;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.UserService;
import by.epam.shop.service.exception.ServiceException;
import by.epam.shop.validator.Validator;
import by.epam.shop.validator.impl.UserValidator;

public class RegisterUserCommand implements ICommand {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String REPEAT = "repeat";
	private static final String NAME = "name";
	private static final String SNAME = "sname";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private static final String MESSAGE = "message";
	private static final String MESSAGE_LOGIN_ERROR = "message";
	private static final String PHONE = "phone";
	private static final String NUMBERS = "numbers";
	private static final String NUMBER = "number";
	private static final Lock LOCK = new ReentrantLock();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Long> numbers = (List<Long>) request.getServletContext().getAttribute(NUMBERS);
		Long number = Long.parseLong(request.getParameter(NUMBER));
		LOCK.lock();
		if (numbers == null) {
			numbers = new ArrayList<Long>();
		} else {
			for (Long i : numbers) {
				if (i.equals(number)) {
					page = PageName.INDEX_PAGE;
					LOCK.unlock();
					return page;
				}
			}

		}
		numbers.add(number);
		request.getServletContext().setAttribute(NUMBERS, numbers);
		LOCK.unlock();
		Validator<User> validator = new UserValidator();
		UserService userService = ServiceFactory.getInstance().getUserService();
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		String name = request.getParameter(NAME);
		String sname = request.getParameter(SNAME);
		String email = request.getParameter(EMAIL);
		String repeat = request.getParameter(REPEAT);
		String address = request.getParameter(ADDRESS);
		String phone = request.getParameter(PHONE);
		if (!(repeat.equals(password))) {
			request.setAttribute(MESSAGE, MessageManager.DIFFERENT_PASSWORDS);
			page = PageName.REGISTER_PAGE;
			return page;
		}
		User user = new User(login, password, name, sname, email, address, phone);
		String result = validator.isValid(user);
		if (result == null) {
			try {
				if (!userService.isUserExist(login, email)) {
					password = PasswordCoding.md5Coding(password);
					user.setPassword(password);
					user = userService.registerUser(user);
					if (user != null) {
						request.getSession(true).setAttribute(LOGIN, user);
						page = PageName.INDEX_PAGE;
					} else {
						request.setAttribute(MESSAGE, MESSAGE_LOGIN_ERROR);
						page = PageName.REGISTER_PAGE;
					}
				} else {
					request.setAttribute(MESSAGE, MessageManager.USER_EXIST);
					page = PageName.REGISTER_PAGE;
				}
			} catch (ServiceException e) {
				LOGGER.error(e);
				page = PageName.ERROR_PAGE;
			}
		}

		else {
			request.setAttribute(MESSAGE, result);
			page = PageName.REGISTER_PAGE;
		}
		return page;
	}

}
