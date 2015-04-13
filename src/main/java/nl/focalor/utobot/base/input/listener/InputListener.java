package nl.focalor.utobot.base.input.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	private final List<IInputHandlerFactory> factories = new ArrayList<>();
	private final List<IRegexHandler> regexHandlers = new ArrayList<>();
	private final List<ICommandHandler> commandHandlers = new ArrayList<>();
	private final Map<String, ICommandHandler> commandToCommandHandler = new HashMap<>();

	@Autowired(required = false)
	public void setRegexHandlers(List<IRegexHandler> regexHandlers) {
		this.regexHandlers.addAll(regexHandlers);
	}

	@Autowired(required = false)
	public void setCommandHandlers(List<ICommandHandler> commandHandlers) {
		this.commandHandlers.addAll(commandHandlers);
		for (ICommandHandler handler : commandHandlers) {
			for (String commandName : handler.getCommandNames()) {
				this.commandToCommandHandler.put(commandName.toLowerCase(), handler);
			}
		}
	}

	@Autowired(required = false)
	public void setInputHandlerFactories(List<IInputHandlerFactory> inputHandlerFactories) {
		this.factories.addAll(inputHandlerFactories);
		for (IInputHandlerFactory factory : factories) {
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
		ICommandHandler handler = commandToCommandHandler.get(command.getCommand().toLowerCase());
		if (handler != null) { // ignore unknown commands
			return handler.handleCommand(command);
		}
		return null;
	}

	@Override
	public Set<IRegexHandler> getRegexHandlers() {
		return new HashSet<>(regexHandlers);
	}

	@Override
	public Set<ICommandHandler> getCommandHandlers() {
		return new HashSet<>(commandHandlers);
	}

	@Override
	public Set<IInputHandlerFactory> getFactories() {
		return new HashSet<>(factories);
	}

}
