package test;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.shop.dao.DAOFactory;
import by.epam.shop.dao.UserDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.entity.User;
import by.epam.shop.resource.PasswordCoding;

public class UserDaoTest {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static UserDao userDao;
	private static ConnectionPool connectionPool;

	@BeforeClass
	public static void initConnectionPool() {
		try {
			connectionPool = ConnectionPool.getInstance();
			connectionPool.initPoolData();
		} catch (ConnectionPoolException e) {
			LOGGER.error(e);
		}
	}

	@Test
	public void testFindUser() {
		userDao = DAOFactory.getInstance().getUserDao();
		User user = new User();
		user.setLogin("Kraft");
		user.setName("Vasya");
		user.setSname("Kotov");
		user.setEmail("abc@gmail.com");
		String password = PasswordCoding.md5Coding("12345");
		user.setPassword(password);
		user.setPhone("12345");
		user.setBlackList(false);
		try {
			userDao.addUser(user);
			String login = user.getLogin();
			password = user.getPassword();
			user = userDao.findUser(login, password);
			assertEquals("Kraft", user.getLogin());
			assertEquals("Vasya", user.getName());
			assertEquals("Kotov", user.getSname());
			assertEquals("abc@gmail.com", user.getEmail());
			assertEquals("12345", user.getPhone());
			assertEquals(PasswordCoding.md5Coding("12345"), user.getPassword());
			userDao.deleteUser(login);
		} catch (DAOException e) {
			LOGGER.error(e);
		}
		return;
	}

	@Test
	public void testAddToBlacklist() {
		userDao = DAOFactory.getInstance().getUserDao();
		User user = new User();
		user.setLogin("Kraft");
		user.setName("Vasya");
		user.setSname("Kotov");
		user.setEmail("abc@gmail.com");
		String password = PasswordCoding.md5Coding("12345");
		user.setPassword(password);
		user.setBlackList(false);
		try {
			userDao.addUser(user);
			String login = user.getLogin();
			userDao.addToBlacklist(login);
			user = userDao.findUser(login, password);
			assertEquals(true, user.isBlackList());
			userDao.deleteUser(login);
		} catch (DAOException e) {
			LOGGER.error(e);
		}
		return;
	}

	@AfterClass
	public static void closeConnectionPool() {
		try {
			connectionPool.destroyConnectionPool();
		} catch (ConnectionPoolException e) {
			LOGGER.error(e);
		}
	}

}
