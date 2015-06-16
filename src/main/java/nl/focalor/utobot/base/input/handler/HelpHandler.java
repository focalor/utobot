package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class HelpHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "help";

	public HelpHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		if (StringUtils.isEmpty(event.getArgument())) {
			return buildGeneralHelp(event);
		} else {
			return buildSpecificHelp(event);
		}
	}

	private IResult buildSpecificHelp(CommandInput event) {
		String argument = event.getArgument();
		IInputListener dispatcher = event.getDispatcher();

		// Check commandhandlers
		for (ICommandHandler handler : dispatcher.getCommandHandlers()) {
			if (handler.getCommandNames().contains(argument)) {
				return new MultiReplyResult(handler.getHelp());
			}
		}

		// Check regexhandlers
		for (IRegexHandler handler : dispatcher.getRegexHandlers()) {
			if (handler.getName().equals(argument)) {
				return new MultiReplyResult(handler.getHelp());
			}
		}

		// Check factories
		for (IInputHandlerFactory factory : dispatcher.getFactories()) {
			if (factory.getName().equals(argument)) {
				return new MultiReplyResult(factory.getHelp());
			}
		}

		return new ErrorResult("Specified help command '" + argument + "' is unknown");
	}

	private IResult buildGeneralHelp(IInput input) {
		IInputListener dispatcher = input.getDispatcher();

		List<String> messages = new ArrayList<>();
		messages.add("Type !help COMMAND for extra information on a command/other input");
		messages.add("Known commands:");
		messages.addAll(getHelpCommandHandlers(dispatcher));
		messages.add("Other supported input:");
		messages.addAll(map(dispatcher.getFactories()));
		messages.addAll(map(dispatcher.getRegexHandlers()));
		return new MultiReplyResult(messages);
	}

	//@formatter:off
	private List<String> getHelpCommandHandlers(IInputListener dispatcher) {
		return dispatcher.getCommandHandlers().stream()
				.filter(handler -> handler.hasHelp())
				.map(handler -> handler.getName())
				.sorted()
				.collect(Collectors.toList());
	}

	private List<String> map(Collection<? extends IInputHandler> handlers) {
		return handlers.stream()
				.filter(handler -> handler.hasHelp())
				.map(handler -> handler.getName() + " - " + handler.getSimpleHelp())
				.sorted()
				.collect(Collectors.toList());
	}
	//@formatter:on

	@Override
	public List<String> getHelpBody() {
		return Arrays.asList("!help | shows general help information",
				"!help COMMAND | shows information for the specified command");
	}

	@Override
	public String getSimpleHelp() {
		return "Shows this help information";
	}
}
