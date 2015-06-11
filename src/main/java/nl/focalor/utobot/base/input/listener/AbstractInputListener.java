package nl.focalor.utobot.base.input.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IGenericCommandHandler;
import nl.focalor.utobot.base.input.handler.IGenericInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IGenericRegexHandler;
import nl.focalor.utobot.base.input.handler.IInputHandlerFactory;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractInputListener implements IInputListener, ApplicationContextAware {
	private final List<IInputHandlerFactory> factories = new ArrayList<>();
	private final List<IRegexHandler> regexHandlers = new ArrayList<>();
	private final List<ICommandHandler> commandHandlers = new ArrayList<>();
	private final Map<String, ICommandHandler> commandToCommandHandler = new HashMap<>();
	private ApplicationContext applicationContext;

	@PostConstruct
	// Sorry for the ugly @PostConstruct combined with ApplicationContextAware
	// Spring doesnt like circular bean references on non-singletons
	public void init() {
		addRegexHandlers(applicationContext.getBeansOfType(IGenericRegexHandler.class).values());
		addCommandHandlers(applicationContext.getBeansOfType(IGenericCommandHandler.class).values());
		addInputHandlerFactories(applicationContext.getBeansOfType(IGenericInputHandlerFactory.class).values());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void addRegexHandlers(Collection<? extends IRegexHandler> regexHandlers) {
		this.regexHandlers.addAll(regexHandlers);
	}

	public void addCommandHandlers(Collection<? extends ICommandHandler> commandHandlers) {
		this.commandHandlers.addAll(commandHandlers);
		for (ICommandHandler handler : commandHandlers) {
			for (String commandName : handler.getCommandNames()) {
				this.commandToCommandHandler.put(commandName.toLowerCase(), handler);
			}
		}
	}

	public void addInputHandlerFactories(Collection<? extends IInputHandlerFactory> inputHandlerFactories) {
		this.factories.addAll(inputHandlerFactories);
		for (IInputHandlerFactory factory : factories) {
			addRegexHandlers(factory.getRegexHandlers());
			addCommandHandlers(factory.getCommandHandlers());
		}
	}

	@Override
	public IResult onMessage(String room, String user, String message) {
		if (StringUtils.isEmpty(message)) {
			return NoReplyResult.NO_REPLY;
		} else if (message.charAt(0) == CommandInput.COMMAND_PREFIX) {
			CommandInput input = CommandInput.createFor(this, room, user, message);
			return onCommand(input);
		} else {
			Input input = new Input(this, room, user, message);
			return onNonCommand(input);
		}
	}

	@Override
	public IResult onNonCommand(IInput input) {
		for (IRegexHandler handler : regexHandlers) {
			if (handler.find(input)) {
				return handler.handleInput(input);
			}
		}
		return NoReplyResult.NO_REPLY;
	}

	@Override
	public IResult onCommand(CommandInput command) {
		ICommandHandler handler = commandToCommandHandler.get(command.getCommand().toLowerCase());
		if (handler != null) { // ignore unknown commands
			return handler.handleCommand(command);
		}
		return NoReplyResult.NO_REPLY;
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
