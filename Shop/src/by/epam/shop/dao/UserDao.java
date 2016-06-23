package by.epam.shop.dao;

import java.util.List;

import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.User;

public interface UserDao {

	User findUser(String login, String password) throws DAOException;

	void addUser(User user) throws DAOException;

	boolean checkUser(String login, String email) throws DAOException;

	List<User> getAllUsers() throws DAOException;

	void addToBlacklist(String login) throws DAOException;

	void removeFromBlacklist(String login) throws DAOException;

	void deleteUser(String login) throws DAOException;
}
