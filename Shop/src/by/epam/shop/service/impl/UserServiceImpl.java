package by.epam.shop.service.impl;

import java.util.List;

import by.epam.shop.dao.DAOFactory;
import by.epam.shop.dao.UserDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.User;
import by.epam.shop.resource.PasswordCoding;
import by.epam.shop.service.UserService;
import by.epam.shop.service.exception.ServiceException;

public final class UserServiceImpl implements UserService {

	public final User signIn(String login, String password) throws ServiceException {
		User user = null;
		if (login == null || password == null) {
			return user;
		}
		password = PasswordCoding.md5Coding(password);
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			user = userDao.findUser(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public User registerUser(User user) throws ServiceException {
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			userDao.addUser(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public boolean isUserExist(String login, String email) throws ServiceException {
		boolean result = false;
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			result = userDao.checkUser(login, email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public List<User> allUsers() throws ServiceException {
		List<User> users = null;
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			users = userDao.getAllUsers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return users;
	}

	@Override
	public void addToBlacklist(String login) throws ServiceException {
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			userDao.addToBlacklist(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteUser(String login) throws ServiceException {
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			userDao.deleteUser(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void removeFromBlacklist(String login) throws ServiceException {
		UserDao userDao = DAOFactory.getInstance().getUserDao();
		try {
			userDao.removeFromBlacklist(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

}
