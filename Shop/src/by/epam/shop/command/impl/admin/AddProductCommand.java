package by.epam.shop.command.impl.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

public class AddProductCommand implements ICommand {
	private final static String NAME = "name";
	private final static String PRICE = "price";
	private final static String DESCRIPTION = "description";
	private static final String IMAGE_MIME_TYPE = "image/";
	private final static String GENRE = "genre";
	private final static String AUTHOR = "author";
	private final static String MESSAGE = "message";
	private final static String FILE = "file";
	private final static String LOGIN = "login";
	private final static String PICTURE_UPLOAD_PATH = "img" + File.separator + "albums" + File.separator;
	private final static String NO_IMAGE = "no_image.jpg";
	private final static Logger LOGGER = LogManager.getRootLogger();
	private static final String NUMBERS = "numbers";
	private static final String NUMBER = "number";
	private static final Lock LOCK = new ReentrantLock();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		List<Long> numbers = (List<Long>) request.getServletContext().getAttribute(NUMBERS);
		Long number = Long.parseLong(request.getParameter(NUMBER));
		LOCK.lock();
		if (numbers == null) {
			numbers = new ArrayList<Long>();
		} else {
			for (Long i : numbers) {
				if (i.equals(number)) {
					page = PageName.ADMIN_PAGE;
					LOCK.unlock();
					return page;
				}
			}

		}
		numbers.add(number);
		request.getServletContext().setAttribute(NUMBERS, numbers);
		LOCK.unlock();
		Product product = null;
		String filename = null;
		String image = null;
		Validator<Product> validator = new ProductValidator();
		ProductService productService = ServiceFactory.getInstance().getProductService();
		String name = request.getParameter(NAME);
		String price = request.getParameter(PRICE).replace(',', '.');
		String desc = request.getParameter(DESCRIPTION);
		String genre = request.getParameter(GENRE);
		String author = request.getParameter(AUTHOR);
		User user = (User) request.getSession().getAttribute(LOGIN);

		if (user == null) {
			page = PageName.REGISTER_PAGE;
			request.setAttribute(MESSAGE, MessageManager.LOGIN_REGISTER);
			return page;
		}

		if (name.isEmpty() || price.isEmpty() || genre.isEmpty() || author.isEmpty()) {
			request.setAttribute(MESSAGE, MessageManager.EMPTY_FIELD);
			page = PageName.ADMIN_PAGE;
			return page;
		}

		try {
			Part filePart = request.getPart(FILE);
			filename = filePart.getSubmittedFileName();
			if (!filename.isEmpty()) {
				String mimeType = request.getServletContext().getMimeType(filename);
				if (mimeType.startsWith(IMAGE_MIME_TYPE)) {
					File uploads = new File(request.getServletContext().getRealPath("") + PICTURE_UPLOAD_PATH);
					File file = new File(uploads, filename);
					try (InputStream input = filePart.getInputStream()) {
						Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

					}
					image = filename;
				} else {
					request.setAttribute(MESSAGE, MessageManager.NOT_IMAGE);
					page = PageName.ADMIN_PAGE;
					return page;
				}
			} else {
				image = NO_IMAGE;
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
		String result = validator.isValid(product);
		if (result == null) {
			try {

				productService.addProduct(product);
				page = PageName.ADMIN_PAGE;
			} catch (ServiceException e) {
				LOGGER.error(e);
				page = PageName.ERROR_PAGE;
				request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			}
		} else {
			page = PageName.ADMIN_PAGE;
			request.setAttribute(MESSAGE, result);
		}
		return page;
	}

}
