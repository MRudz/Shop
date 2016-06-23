package test;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.shop.dao.DAOFactory;
import by.epam.shop.dao.ProductDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.entity.Product;

public class ProductDaoTest {
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static ProductDao productDao;
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
	public void testFindById() {
		productDao = DAOFactory.getInstance().getProductDao();
		try {
			Product product = productDao.findById(2);
			assertEquals("TheBlack", product.getName());
			assertEquals("rock", product.getGenre());
			assertEquals("Asking Alexandria", product.getAuthor());
			assertEquals(8.44, product.getPrice(), 0);
		} catch (DAOException e) {
			LOGGER.error(e);
		}
		return;
	}

	@Test
	public void testDeleteProduct() {
		productDao = DAOFactory.getInstance().getProductDao();
		Product product = new Product();
		product.setName("Album");
		product.setAuthor("Adele");
		product.setGenre("rock");
		product.setPrice(7.45);
		try {
			productDao.addProduct(product);
			int id = product.getId();
			productDao.deleteProduct(id);
			assertEquals(null, productDao.findById(id));
		} catch (DAOException e) {
			LOGGER.error(e);
		}

		return;

	}

	@Test
	public void testAddProduct() {
		productDao = DAOFactory.getInstance().getProductDao();
		Product product = new Product();
		product.setName("Album");
		product.setAuthor("Adele");
		product.setGenre("rock");
		product.setPrice(7.45);
		try {
			productDao.addProduct(product);
			int id = product.getId();
			product = productDao.findById(id);
			assertEquals("Album", product.getName());
			assertEquals("Adele", product.getAuthor());
			assertEquals("rock", product.getGenre());
			assertEquals(7.45, product.getPrice(), 0);
			productDao.deleteProduct(id);
		} catch (DAOException e) {
			LOGGER.error(e);
		}

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
