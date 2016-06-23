package by.epam.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epam.shop.dao.UserDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.dao.pool.ConnectionPool;
import by.epam.shop.dao.pool.exception.ConnectionPoolException;
import by.epam.shop.entity.User;

public class UserDaoImpl implements UserDao {
	private final static String SQL_FIND_USER = "SELECT user_login,user_password, user_role,user_email, user_first_name, user_last_name, user_address, user_phone, user_isblack FROM users WHERE user_login=? AND user_password=?";
	private final static String SQL_ADD_USER = "INSERT into users (user_login,user_password, user_role, user_email, user_first_name, user_last_name, user_address, user_phone) values(?,?,?,?,?,?,?,?)";
	private final static String SQL_LOGIN_USER = "SELECT user_login from users WHERE user_login=? OR user_email=?";
	private final static String SQL_ALL_USERS = "SELECT user_login,user_password, user_role,user_email, user_first_name, user_last_name, user_address,user_phone, user_isblack FROM users";
	private final static String SQL_TO_BLACKLIST = "UPDATE users SET user_isblack=true WHERE user_login=?";
	private final static String SQL_FROM_BLACKLIST = "UPDATE users SET user_isblack=false WHERE user_login=?";
	private final static String SQL_DELETE_USER = "DELETE FROM users WHERE user_login = ?";

	public final User findUser(String login, String password) throws DAOException {
		User user = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_USER);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setLogin(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setRole(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setName(resultSet.getString(5));
				user.setSname(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhone(resultSet.getString(8));
				user.setBlackList(resultSet.getBoolean(9));
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

		return user;
	}

	@Override
	public void addUser(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_ADD_USER);
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getRole());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getName());
			preparedStatement.setString(6, user.getSname());
			preparedStatement.setString(7, user.getAddress());
			preparedStatement.setString(8, user.getPhone());
			if (preparedStatement.executeUpdate() == 0) {
				throw new DAOException("Creating user failed!");
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
	public boolean checkUser(String login, String email) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean result = false;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_LOGIN_USER);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, email);
			resultSet = preparedStatement.executeQuery();
			result = resultSet.first();
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

	@Override
	public List<User> getAllUsers() throws DAOException {
		List<User> users = new ArrayList<User>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_ALL_USERS);
			while (resultSet.next()) {
				User user = new User();
				user.setLogin(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setRole(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setName(resultSet.getString(5));
				user.setSname(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhone(resultSet.getString(8));
				user.setBlackList(resultSet.getBoolean(9));
				users.add(user);
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
		return users;
	}

	@Override
	public void addToBlacklist(String login) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_TO_BLACKLIST);
			preparedStatement.setString(1, login);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Adding user to blacklist failed, no rows affected.");
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
	public void deleteUser(String login) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
			preparedStatement.setString(1, login);
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
	public void removeFromBlacklist(String login) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			preparedStatement = connection.prepareStatement(SQL_FROM_BLACKLIST);
			preparedStatement.setString(1, login);
			if (preparedStatement.executeUpdate() == 0) {
				throw new SQLException("Removing user from blacklist failed, no rows affected.");
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
}
