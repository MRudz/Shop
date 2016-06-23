package by.epam.shop.command.impl.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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
import by.epam.shop.validator.Validator;
import by.epam.shop.validator.impl.ProductValidator;

public class EditProductCommand implements ICommand {
	private final static Logger LOGGER = LogManager.getRootLogger();
	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String GENRE = "genre";
	private final static String AUTHOR = "author";
	private final static String PRICE = "price";
	private final static String DESCRIPTION = "description";
	private final static String MESSAGE = "message";
	private final static String FILE = "file";
	private final static String LOGIN = "login";
	private final static String IMAGE_MIME_TYPE = "image/";
	private final static String PICTURE_UPLOAD_PATH = "img" + File.separator + "albums" + File.separator;
	private final static String IMAGE = "image";

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		Product product = null;
		String filename = null;
		ProductService productService = null;
		int id = Integer.parseInt(request.getParameter(ID));
		String name = request.getParameter(NAME);
		String genre = request.getParameter(GENRE);
		String author = request.getParameter(AUTHOR);
		String price = request.getParameter(PRICE).replace(',', '.');
		String desc = request.getParameter(DESCRIPTION);
		String image = request.getParameter(IMAGE);
		Validator<Product> validator = new ProductValidator();
		User user = (User) request.getSession().getAttribute(LOGIN);
		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}
		if (name.isEmpty() || price.isEmpty() || genre.isEmpty() || author.isEmpty()) {
			request.setAttribute(MESSAGE, MessageManager.EMPTY_FIELD);
			page = PageName.EDIT_PAGE;
			return page;
		}

		try {
			Part filePart = request.getPart(FILE);
			filename = filePart.getSubmittedFileName();
			if (!filename.isEmpty()) {
				String mimeType = request.getServletContext().getMimeType(filename);
				if (mimeType.startsWith(IMAGE_MIME_TYPE)) {
					File uploads = new File(request.getServletContext().getRealPath("") + PICTURE_UPLOAD_PATH);
					File newFile = new File(uploads, filename);
					try (InputStream input = filePart.getInputStream()) {
						Files.copy(input, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
					image = filename;
				} else {
					request.setAttribute(MESSAGE, MessageManager.NOT_IMAGE);
					page = PageName.ADMIN_PAGE;
					return page;
				}
			}
		} catch (IOException | ServletException e) {
			LOGGER.error(e);
		}

		product = new Product();
		product.setName(name);
		product.setPrice(Double.parseDouble(price));
		product.setDesc(desc);
		product.setGenre(genre);
		product.setAuthor(author);
		product.setImage(image);
		product.setId(id);
		String result = validator.isValid(product);
		if (result == null) {
			try {
				productService = ServiceFactory.getInstance().getProductService();
				productService.editProduct(product);
				page = PageName.INDEX_PAGE;
			} catch (ServiceException e) {
				LOGGER.error(e);
				page = PageName.ERROR_PAGE;
				request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			}
		} else {
			page = PageName.EDIT_PAGE;
			request.setAttribute(MESSAGE, result);
		}
		return page;
	}

}
