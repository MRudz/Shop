package by.epam.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epam.shop.dao.ProductDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.entity.Product;

public class ProductDaoImpl implements ProductDao {
	private final static String SQL_GET_PRODUCTS = "SELECT id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album, is_sale, rate_album FROM albums INNER JOIN authors ON albums.id_author = authors.id_author";
	private final static String SQL_GET_PRODUCTS_BY_GENRE = "SELECT id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album , is_sale, rate_album FROM albums INNER JOIN authors ON albums.id_author = authors.id_author where name_genre=?";
	private final static String SQL_DELETE_PRODUCT = "DELETE FROM albums WHERE id_album=?";
	private final static String SQL_ADD_PRODUCT = "INSERT INTO albums (name_album,name_genre,id_author,price_album,image_album,desc_en_album) VALUES (?,?,?,?,?,?)";
	private final static String SQL_GET_ID_AUTHOR = "SELECT id_author FROM authors WHERE name_author = ?";
	private final static String SQL_ADD_AUTHOR = "INSERT INTO authors (name_author) VALUES(?)";
	private final static String SQL_FIND_PRODUCTS = "SELECT id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album, is_sale, rate_album FROM albums INNER JOIN authors ON albums.id_author = authors.id_author WHERE name_album LIKE ? ";
	private final static String SQL_FIND_ID = "SELECT id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album, is_sale FROM albums INNER JOIN authors ON albums.id_author = authors.id_author where id_album=?";
	private static final String SQL_UPDATE_PRODUCT = "UPDATE albums SET name_album=?, name_genre=?, id_author=?, price_album=?,  desc_en_album=?, image_album=? WHERE id_album = ?";
	private static final String SQL_CHANGE_SALE = "UPDATE albums SET is_sale=? WHERE id_album=?";
	private static final String SQL_BESTSELLERS = "SELECT id_album, name_album, name_genre, name_author, price_album, image_album, desc_en_album, is_sale, rate_album FROM albums INNER JOIN authors ON albums.id_author = authors.id_author ORDER BY sales_album DESC LIMIT 0,2";
	private static final String SQL_CHECK_RATE = "SELECT rating FROM users_albums WHERE user_login=? AND id_album = ?";
	private static final String SQL_UPDATE_MARK = "UPDATE users_albums SET rating = ? WHERE user_login=? AND id_album=?";
	private static final String SQL_ADD_MARK = "INSERT INTO users_albums (rating,user_login,id_album) VALUES (?,?,?)";
	private static final String SQL_MARK_AMOUNT = "SELECT COUNT(*), SUM(rating) FROM users_albums WHERE id_album = ? ";
	private static final String SQL_UPDATE_RATE = "UPDATE albums SET rate_album = ? WHERE id_album = ?";

	@Override
	public List<Product> getProducts(String genre) throws DAOException {
		List<Product> products = new ArrayList<Product>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			if (genre == null) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(SQL_GET_PRODUCTS);
			} else {
				preparedStatement = connection.prepareStatement(SQL_GET_PRODUCTS_BY_GENRE);
				preparedStatement.setString(1, genre);
				resultSet = preparedStatement.executeQuery();
			}
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setGenre(resultSet.getString(3));
				product.setAuthor(resultSet.getString(4));
				product.setPrice(resultSet.getDouble(5));
				product.setImage(resultSet.getString(6));
				product.setDesc(resultSet.getString(7));
				product.setSale(resultSet.getBoolean(8));
				product.setRate(resultSet.getDouble(9));
				products.add(product);
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DAOException(e);
		} finally {
			if (statement != null) {
				try {
					ConnectionPool.getInstance().closeConnection(connection, statement, resultSet);
				} catch (ConnectionPoolException e) {
					throw new DAOException(e);
				}
			} else {
				try {
					ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
				} catch (ConnectionPoolException e) {
					throw new DAOException(e);
				}
			}

		}
		return products;
	}

	@Override
	public void deleteProduct(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT);
			preparedStatement.setInt(1, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Deleting failed");
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

	}

	@Override
	public void addProduct(Product product) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int idAuthor = 0;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			idAuthor = getIdAuthor(product.getAuthor(), connection);
			preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getGenre());
			preparedStatement.setInt(3, idAuthor);
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setString(5, product.getImage());
			preparedStatement.setString(6, product.getDesc());
			if (preparedStatement.executeUpdate() == 0) {
				throw new DAOException("Adding product failed");
			}
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating product failed, no ID obtained.");
				}
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

	}

	@Override
	public List<Product> findProductsByName(String name) throws DAOException {
		List<Product> products = new ArrayList<Product>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String regExp = "%" + name + "%";
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCTS);
			preparedStatement.setString(1, regExp);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setGenre(resultSet.getString(3));
				product.setAuthor(resultSet.getString(4));
				product.setPrice(resultSet.getDouble(5));
				product.setImage(resultSet.getString(6));
				product.setDesc(resultSet.getString(7));
				product.setSale(resultSet.getBoolean(8));
				product.setRate(resultSet.getDouble(9));
				products.add(product);
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
		return products;
	}

	@Override
	public Product findById(int id) throws DAOException {
		Connection connection = null;
		Product product = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				product = new Product();
				product.setId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setGenre(resultSet.getString(3));
				product.setAuthor(resultSet.getString(4));
				product.setPrice(resultSet.getDouble(5));
				product.setImage(resultSet.getString(6));
				product.setDesc(resultSet.getString(7));
				product.setSale(resultSet.getBoolean(8));

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
		return product;
	}

	@Override
	public void editProduct(Product product) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionPool.getInstance().takeConnection();
			int idAuthor = getIdAuthor(product.getAuthor(), connection);
			preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getGenre());
			preparedStatement.setInt(3, idAuthor);
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setString(5, product.getDesc());
			preparedStatement.setString(6, product.getImage());
			preparedStatement.setInt(7, product.getId());
			

			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Updating failed!");
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

	}

	@Override
	public void changeSale(int id, boolean sale) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_CHANGE_SALE);
			sale = !sale;
			preparedStatement.setBoolean(1, sale);
			preparedStatement.setInt(2, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Updating failed");
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

	}

	@Override
	public List<Product> getBestSellers() throws DAOException {
		List<Product> bestSellers = new ArrayList<Product>();
		Product product = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_BESTSELLERS);
			while (resultSet.next()) {
				product = new Product();
				product.setId(resultSet.getInt(1));
				product.setName(resultSet.getString(2));
				product.setGenre(resultSet.getString(3));
				product.setAuthor(resultSet.getString(4));
				product.setPrice(resultSet.getDouble(5));
				product.setImage(resultSet.getString(6));
				product.setDesc(resultSet.getString(7));
				product.setSale(resultSet.getBoolean(8));
				product.setRate(resultSet.getDouble(9));
				bestSellers.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {

			try {
				ConnectionPool.getInstance().closeConnection(connection, statement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
		return bestSellers;
	}

	@Override
	public void rateProduct(String login, int id, int rate) throws DAOException {
		Connection connection = null;
		ResultSet resultSet = null;
		double rating = rate;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_CHECK_RATE);
			preparedStatement.setString(1, login);
			preparedStatement.setInt(2, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (rate == resultSet.getInt(1)) {
					return;
				} else {
					updateMark(login, id, rate, connection);
				}
			} else {
				addMark(login, id, rate, connection);
			}
			rating = countRate(id, rate, connection);
			updateRate(rating, id, connection);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		} finally {

			try {
				ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
	}

	private void updateRate(double rating, int id, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_UPDATE_RATE);
			preparedStatement.setDouble(1, rating);
			preparedStatement.setInt(2, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Updating failed");
			}
		} finally {
			preparedStatement.close();
		}
	}

	private double countRate(int id, int rate, Connection connection) throws SQLException {
		double rating = rate;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_MARK_AMOUNT);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			int amount = 1;
			double sumRate = 1;
			if (resultSet.next()) {
				amount = resultSet.getInt(1);
				sumRate = resultSet.getInt(2);
			}
			if (amount != 0 && sumRate != 0) {
				rating = sumRate / amount;
			}
		} finally {
			preparedStatement.close();
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return rating;

	}

	private void updateMark(String login, int id, int rate, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_UPDATE_MARK);
			preparedStatement.setInt(1, rate);
			preparedStatement.setString(2, login);
			preparedStatement.setInt(3, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Updating failed");
			}
		} finally {
			preparedStatement.close();
		}
	}

	private void addMark(String login, int id, int rate, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_ADD_MARK);
			preparedStatement.setInt(1, rate);
			preparedStatement.setString(2, login);
			preparedStatement.setInt(3, id);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Adding failed");
			}
		} finally {
			preparedStatement.close();
		}
	}

	private int getIdAuthor(String name, Connection connection) throws SQLException {
		int id = 0;

		PreparedStatement preparedStatement = null;
		PreparedStatement addAuthorPreparedStatement = null;
		ResultSet resultSet = null;
		ResultSet generatedKeys = null;
		try {
			preparedStatement = connection.prepareStatement(SQL_GET_ID_AUTHOR);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}

			else {
				addAuthorPreparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR,
						Statement.RETURN_GENERATED_KEYS);
				addAuthorPreparedStatement.setString(1, name);
				if (addAuthorPreparedStatement.executeUpdate() == 0) {
					throw new SQLException("Adding author failed");
				}
				generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating product failed, no ID obtained.");
				}

			}
		} finally {
			preparedStatement.close();
			if (addAuthorPreparedStatement != null) {
				addAuthorPreparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (generatedKeys != null) {
				generatedKeys.close();
			}
		}

		return id;
	}

}
