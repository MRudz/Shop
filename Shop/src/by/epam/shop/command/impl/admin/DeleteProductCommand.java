package by.epam.shop.command.impl.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;
import by.epam.shop.entity.User;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class DeleteProductCommand implements ICommand {
	private static final String ID = "id";
	private static final String MESSAGE = "message";
	private static final String PRODUCTS = "products";
	private static final String IMAGE = "image";
	private static final String LOGIN = "login";
	private final static String PICTURE_UPLOAD_PATH = "img" + File.separator + "albums" + File.separator;
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String NO_IMAGE = "no_image.jpg";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		String genre = null;
		String fileName = null;
		List<Product> products;
		int id = Integer.parseInt(request.getParameter(ID));
		fileName = request.getParameter(IMAGE);
		ProductService productService = ServiceFactory.getInstance().getProductService();
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		try {
			productService.deleteProduct(id);
			if (!(fileName.equals(NO_IMAGE))) {
				File uploads = new File(request.getServletContext().getRealPath("") + PICTURE_UPLOAD_PATH);
				File file = new File(uploads, fileName);
				Files.delete(file.toPath());
			}
			products = productService.getAllProducts(genre);
			request.getSession().setAttribute(PRODUCTS, products);
			page = PageName.INDEX_PAGE;
		} catch (ServiceException | IOException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
