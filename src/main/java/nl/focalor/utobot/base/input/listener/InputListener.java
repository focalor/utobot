package nl.focalor.utobot.base.input.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputListener implements IInputListener {
	private final List<IRegexHandler> regexHandlers = new ArrayList<>();
	private final Map<String, ICommandHandler> commandHandlers = new HashMap<>();

	@Autowired(required = false)
	public void setRegexHandlers(List<IRegexHandler> regexHandlers) {
		this.regexHandlers.addAll(regexHandlers);
	}

	@Autowired(required = false)
	public void setCommandHandlers(List<ICommandHandler> commandHandlers) {
		for (ICommandHandler handler : commandHandlers) {
			for (String commandName : handler.getCommandNames()) {
				this.commandHandlers.put(commandName.toLowerCase(), handler);
			}
		}
	}

	@Autowired(required = false)
	public void setInputHandlerFactories(List<IInputHandlerFactory> inputHandlerFactories) {
		for (IInputHandlerFactory factory : inputHandlerFactories) {
			setRegexHandlers(factory.getRegexHandlers());
			setCommandHandlers(factory.getCommandHandlers());
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
		ICommandHandler handler = commandHandlers.get(command.getCommand().toLowerCase());
		if (handler != null) { // ignore unknown commands
			return handler.handleCommand(command);
		}
		return null;
	}

}
