package by.epam.shop.command.impl.navigation;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.epam.shop.command.ICommand;
import by.epam.shop.controller.PageName;
import by.epam.shop.entity.Product;
import by.epam.shop.resource.MessageManager;
import by.epam.shop.service.ProductService;
import by.epam.shop.service.ServiceFactory;
import by.epam.shop.service.exception.ServiceException;

public class ToEditCommand implements ICommand {
	private static final String ID = "id";
	private static final String MESSAGE = "message";
	private static final String PRODUCT = "product";
	private static final Logger LOGGER = LogManager.getRootLogger();

	@Override
	public String execute(HttpServletRequest request) {
		String page = null;
		Product product = null;
		int id = Integer.parseInt(request.getParameter(ID));
		ProductService productService = ServiceFactory.getInstance().getProductService();
		try {
			product = productService.findById(id);
			request.setAttribute(PRODUCT, product);
			page = PageName.EDIT_PAGE;
		} catch (ServiceException e) {
			LOGGER.error(e);
			request.setAttribute(MESSAGE, MessageManager.DATABASE_ERROR);
			page = PageName.ERROR_PAGE;
		}
		return page;
	}

}
