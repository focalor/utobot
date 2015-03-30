package nl.focalor.utobot.base.input.handler;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommandHandler implements ICommandHandler {
	private final List<String> commandNames;

	public AbstractCommandHandler(String commandName) {
		this(Arrays.asList(commandName));
	}

	public AbstractCommandHandler(List<String> commandNames) {
		this.commandNames = commandNames;
	}

	@Override
	public List<String> getCommandNames() {
		return commandNames;
	}
}
