package by.epam.shop.service;

import java.util.List;

import by.epam.shop.entity.Product;
import by.epam.shop.service.exception.ServiceException;

public interface ProductService {
	List<Product> getAllProducts(String genre) throws ServiceException;

	void deleteProduct(int id) throws ServiceException;

	void addProduct(Product product) throws ServiceException;

	List<Product> findProducts(String name) throws ServiceException;

	Product findById(int id) throws ServiceException;

	void editProduct(Product product) throws ServiceException;

	void changeSale(int id, boolean sale) throws ServiceException;

	List<Product> getBestSellers() throws ServiceException;

	void rateProduct(String login, int id, int rate) throws ServiceException;
}
