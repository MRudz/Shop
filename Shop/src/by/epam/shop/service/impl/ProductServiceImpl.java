package by.epam.shop.service.impl;

import java.util.List;

import by.epam.shop.dao.DAOFactory;
import by.epam.shop.dao.ProductDao;
import by.epam.shop.dao.exception.DAOException;
import by.epam.shop.entity.Product;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.exception.ServiceException;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> getAllProducts(String genre) throws ServiceException {
		List<Product> products = null;
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			products = productDao.getProducts(genre);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return products;
	}

	@Override
	public void deleteProduct(int id) throws ServiceException {
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			productDao.deleteProduct(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void addProduct(Product product) throws ServiceException {
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			productDao.addProduct(product);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Product> findProducts(String name) throws ServiceException {
		List<Product> products = null;
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			products = productDao.findProductsByName(name);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return products;
	}

	@Override
	public Product findById(int id) throws ServiceException {
		Product product = null;
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			product = productDao.findById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return product;
	}

	@Override
	public void editProduct(Product product) throws ServiceException {
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			productDao.editProduct(product);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void changeSale(int id, boolean sale) throws ServiceException {
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			productDao.changeSale(id, sale);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Product> getBestSellers() throws ServiceException {
		List<Product> bestSellers = null;
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			bestSellers = productDao.getBestSellers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return bestSellers;
	}

	@Override
	public void rateProduct(String login, int id, int rate) throws ServiceException {
		ProductDao productDao = DAOFactory.getInstance().getProductDao();
		try {
			productDao.rateProduct(login,id,rate);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
