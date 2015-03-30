package nl.focalor.utobot.base.input;

import java.util.List;

import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;

public interface IInputListener {
	public List<IRegexHandler> getRegexHandlers();

	public ICommandHandler getCommandHandler(String lowerCaseName);

	public void put(String lowerCaseName, ICommandHandler handler);

	public default void setCommandHandlers(List<ICommandHandler> handlers) {
		for (ICommandHandler handler : handlers) {
			for (String commandName : handler.getCommandNames()) {
				put(commandName, handler);
			}
		}
	}

	public default IResult onMessage(String message) {
		if (message.charAt(0) == CommandInput.COMMAND_PREFIX) {
			return onCommand(CommandInput.createFor(message));
		} else {
			return onNonCommand(new Input(message));
		}
	}

	public default IResult onNonCommand(IInput input) {
		for (IRegexHandler handler : getRegexHandlers()) {
			if (handler.matches(input)) {
				return handler.handleInput(input);
			}
		}
		return null;
	}

	public default IResult onCommand(CommandInput command) {
		ICommandHandler handler = getCommandHandler(command.getCommand());
		if (handler != null) { // ignore unknown commands
			return handler.handleCommand(command);
		}
		return null;
	}

}
