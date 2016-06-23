package by.epam.shop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shop.command.ICommand;
import by.epam.shop.command.exception.CommandException;
import by.epam.shop.controller.helper.CommandHelper;
import by.epam.shop.controller.helper.InitCommandHelper;

@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getRootLogger();
	private static final String COMMAND_NAME = "command";

	private final CommandHelper commandHelper = new CommandHelper();

	public Controller() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			InitCommandHelper initializer = new InitCommandHelper(this.getServletContext());
			commandHelper.initCommandHelper(initializer);
		} catch (CommandException e) {
			LOGGER.error(e);
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandName = null;
		ICommand command = null;
		String page = null;

		commandName = request.getParameter(COMMAND_NAME);
		command = commandHelper.getCommand(commandName);
		page = command.execute(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}
