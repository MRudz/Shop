package by.epam.shop.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.generator.RandomGenerator;

public class ServletContextListenerImpl implements ServletContextListener {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static ConnectionPool connectionPool;
	private static final String GENERATOR = "generator";
	private static final String BLOCKED_USERS = "blockedUsers";


	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			connectionPool = ConnectionPool.getInstance();
			connectionPool.initPoolData();
			List<String> blockedUsers = new ArrayList<String>();
			servletContextEvent.getServletContext().setAttribute(BLOCKED_USERS, blockedUsers);
			RandomGenerator generator = new RandomGenerator();
			servletContextEvent.getServletContext().setAttribute(GENERATOR, generator);
			LOGGER.info("Connection pool initialized successfully");
		} catch (ConnectionPoolException e) {
			throw new ConnectionPoolNotInitializedException("Connection cannot be initialized", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			connectionPool.destroyConnectionPool();
			LOGGER.info("Connection pool destroyed successfully");
		} catch (ConnectionPoolException e) {
			LOGGER.error("Connection pool not correctly ", e);
		}
	}
}