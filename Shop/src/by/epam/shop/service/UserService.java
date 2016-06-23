package by.epam.shop.service;

import java.util.List;

import by.epam.shop.entity.User;
import by.epam.shop.service.exception.ServiceException;

public interface UserService {
	User signIn(String login, String password) throws ServiceException;

	User registerUser(User user) throws ServiceException;

	boolean isUserExist(String login, String email) throws ServiceException;

	List<User> allUsers() throws ServiceException;

	void addToBlacklist(String login) throws ServiceException;

	void removeFromBlacklist(String login) throws ServiceException;

	void deleteUser(String login) throws ServiceException;
}
