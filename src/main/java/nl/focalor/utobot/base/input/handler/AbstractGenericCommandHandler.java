package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractGenericCommandHandler implements IGenericCommandHandler {
	private final String mainCommand;
	private final List<String> commandNames;

	public AbstractGenericCommandHandler(String commandName) {
		this(commandName, new ArrayList<>(0));
	}

	public AbstractGenericCommandHandler(String commandName, String... alternateCommandNames) {
		this(commandName, Arrays.asList(alternateCommandNames));
	}

	public AbstractGenericCommandHandler(String commandName, List<String> alternateCommandNames) {
		this.mainCommand = commandName;
		this.commandNames = new ArrayList<String>(alternateCommandNames.size() + 1);
		this.commandNames.add(commandName);
		this.commandNames.addAll(alternateCommandNames);
	}

	@Override
	public List<String> getCommandNames() {
		return commandNames;
	}

	@Override
	public String getName() {
		return mainCommand;
	}
}
