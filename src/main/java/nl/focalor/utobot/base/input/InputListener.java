package nl.focalor.utobot.base.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;

import org.apache.commons.lang3.StringUtils;

public class InputListener implements IInputListener {
	private final List<IRegexHandler> regexHandlers;
	private final Map<String, ICommandHandler> handlers = new HashMap<>();

	public InputListener(List<IRegexHandler> regexHandlers,
			List<ICommandHandler> commandHandlers) {
		super();
		this.regexHandlers = regexHandlers;

		for (ICommandHandler handler : commandHandlers) {
			for (String commandName : handler.getCommandNames()) {
				this.handlers.put(commandName.toLowerCase(), handler);
			}
		}
	}

	@Override
	public IResult onMessage(String source, String message) {
		if (StringUtils.isEmpty(message)) {
			return null;
		} else if (message.charAt(0) == CommandInput.COMMAND_PREFIX) {
			return onCommand(CommandInput.createFor(source, message));
		} else {
			return onNonCommand(new Input(source, message));
		}
	}

	@Override
	public IResult onNonCommand(IInput input) {
		for (IRegexHandler handler : regexHandlers) {
			if (handler.find(input)) {
				return handler.handleInput(input);
			}
		}
		return null;
	}

	@Override
	public IResult onCommand(CommandInput command) {
		ICommandHandler handler = handlers.get(command.getCommand()
				.toLowerCase());
		if (handler != null) { // ignore unknown commands
			return handler.handleCommand(command);
		}
		return null;
	}

}
