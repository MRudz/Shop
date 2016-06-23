package by.epam.shop.controller.helper;

import java.util.HashMap;
import java.util.Map;

import by.epam.shop.command.ICommand;
import by.epam.shop.command.impl.UnknownCommand;

public final class CommandHelper {
	private Map<String, ICommand> commands = new HashMap<>();

	public void initCommandHelper(InitCommandHelper initializer) {

		commands = initializer.initCommands();
	}

	public ICommand getCommand(String commandName) {
		ICommand command = null;
		if (commandName != null) {
			commandName = commandName.replace('-', '_');
		}
		command = commands.get(commandName);
		if (command == null) {
			command = new UnknownCommand();
		}

		return command;
	}

}
