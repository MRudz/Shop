package by.epam.shop.dao;

import java.util.List;

import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.Product;

public interface ProductDao {
	List<Product> getProducts(String genre) throws DAOException;

	void deleteProduct(int id) throws DAOException;

	void addProduct(Product product) throws DAOException;

	List<Product> findProductsByName(String name) throws DAOException;

	Product findById(int id) throws DAOException;

	void editProduct(Product product) throws DAOException;

	void changeSale(int id, boolean sale) throws DAOException;

	List<Product> getBestSellers() throws DAOException;

	void rateProduct(String login, int id, int rate) throws DAOException;
}
