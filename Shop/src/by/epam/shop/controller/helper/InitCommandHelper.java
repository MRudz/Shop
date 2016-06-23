package by.epam.shop.controller.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.epam.shop.command.ICommand;
import by.epam.shop.command.exception.CommandException;

public class InitCommandHelper {
	private final static String FILE_NAME = "fileName";
	private final static String COMMAND_TAG = "command";
	private final static String COMMAND_NAME_TAG = "command-name";
	private final static String COMMAND_CLASS_TAG = "command-class";
	private final static Logger LOGGER = LogManager.getRootLogger();
	private InputStream commandsStream;

	public InitCommandHelper(ServletContext context) throws CommandException {
		String fileName = context.getInitParameter(FILE_NAME);
		if (fileName == null || fileName.isEmpty()) {
			throw new CommandException("File not found");
		}
		commandsStream = context.getResourceAsStream(fileName);
		if (commandsStream == null) {
			throw new CommandException("File not found2");
		}

	}

	public  Map<String, ICommand> initCommands() {
		Map<String, ICommand> commandsMap = new HashMap<String, ICommand>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;

			db = dbf.newDocumentBuilder();

			Document document = db.parse(commandsStream);
			Element root = document.getDocumentElement();
			NodeList commandNodes = root.getElementsByTagName(COMMAND_TAG);
			for (int i = 0; i < commandNodes.getLength(); i++) {
				Element commandElement = (Element) commandNodes.item(i);
				String commandClass = getSingleChild(commandElement, COMMAND_CLASS_TAG).getTextContent().trim();
				String commandName = getSingleChild(commandElement, COMMAND_NAME_TAG).getTextContent().trim();
				ICommand command = (ICommand) Class.forName(commandClass).newInstance();
				commandsMap.put(commandName, command);
			}
		} catch (ParserConfigurationException | SAXException | IOException | InstantiationException
				| IllegalAccessException | ClassNotFoundException e) {
			LOGGER.error(e);
		}
		return commandsMap;
	}

	private static Element getSingleChild(Element element, String childName) {
		NodeList nlist = element.getElementsByTagName(childName);
		Element child = (Element) nlist.item(0);
		return child;
	}
}
