package by.epam.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.epam.shop.dao.OrderDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.entity.Order;
import by.epam.shop.entity.Product;

public class OrderDaoImpl implements OrderDao {
	private static final String SQL_ADD_PRODUCTS_TO_ORDER = "INSERT INTO orders_albums (id_order, id_album) VALUES(?,?)";
	private static final String SQL_CREATE_ORDER = "INSERT INTO orders (date_order,user_login,price_order) VALUES(?,?,?)";
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String SQL_USER_ORDERS = "SELECT id_order, date_order, user_login, price_order FROM orders WHERE user_login = ?";
	private static final String SQL_GET_PRODUCTS = "SELECT albums.id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album FROM albums INNER JOIN orders_albums ON albums.id_album = orders_albums.id_album INNER JOIN authors ON albums.id_author = authors.id_author WHERE id_order = ? ";
	private static final String SQL_CHECK_PRODUCT = "SELECT user_login, id_album FROM orders INNER JOIN orders_albums ON orders.id_order=orders_albums.id_order WHERE id_album=? AND user_login = ?";
	private static final String SQL_INCREMENT_SALE = "UPDATE albums SET sales_album = sales_album+1 WHERE id_album=?";

	@Override
	public void createOrder(Order order) throws DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, reportDate);
			preparedStatement.setString(2, order.getUserLogin());
			preparedStatement.setDouble(3, order.getSumPrice());
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Creating order failed, no rows affected.");
			}
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				order.setOrderId(resultSet.getInt(1));
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
	}

	@Override
	public void addProductsToOrder(Order order) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement incrementStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			incrementStatement = connection.prepareStatement(SQL_INCREMENT_SALE);
			preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCTS_TO_ORDER);

			for (Product p : order.getProductsList()) {
				preparedStatement.setInt(1, order.getOrderId());
				preparedStatement.setInt(2, p.getId());
				incrementStatement.setInt(1, p.getId());
				if (incrementStatement.executeUpdate() == 0) {
					throw new SQLException("Incrementing failed");
				}
				if (preparedStatement.executeUpdate() == 0) {
					throw new SQLException("Adding products failed!");
				}
			}
			incrementStatement.close();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
	}

	@Override
	public List<Order> findUserOrders(String login) throws DAOException {
		List<Order> orders = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_USER_ORDERS);
			preparedStatement.setString(1, login);
			resultSet = preparedStatement.executeQuery();
			orders = new ArrayList<Order>();
			while (resultSet.next()) {
				Order order = new Order();
				order.setOrderId(resultSet.getInt(1));
				order.setDate(resultSet.getString(2));
				order.setUserLogin(resultSet.getString(3));
				order.setSumPrice(resultSet.getDouble(4));
				orders.add(order);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}

		return orders;
	}

	@Override
	public List<Product> productsToOrder(Order order) throws DAOException {
		List<Product> products = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_PRODUCTS);
			preparedStatement.setInt(1, order.getOrderId());
			resultSet = preparedStatement.executeQuery();
			products = new ArrayList<Product>();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setGenre(resultSet.getString(3));
				product.setAuthor(resultSet.getString(4));
				product.setPrice(resultSet.getDouble(5));
				product.setImage(resultSet.getString(6));
				product.setDesc(resultSet.getString(7));
				products.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
		return products;
	}

	@Override
	public boolean checkProduct(int id, String login) throws DAOException {
		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_CHECK_PRODUCT);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, login);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {
			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
		return result;
	}

}
