package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class HelpHandler extends AbstractCommandHandler {
	public static final String COMMAND_NAME = "help";
	@Autowired
	private IInputListener inputListener;

	public HelpHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput event) {
		if (StringUtils.isEmpty(event.getArgument())) {
			return buildGeneralHelp();
		} else {
			return buildSpecificHelp(event);
		}
	}

	private IResult buildSpecificHelp(CommandInput event) {
		// Check commandhandlers
		String argument = event.getArgument();

		for (ICommandHandler handler : inputListener.getCommandHandlers()) {
			if (handler.getCommandNames().contains(argument)) {
				return new MultiReplyResult(handler.getHelp());
			}
		}

		// Check regexhandlers
		for (IRegexHandler handler : inputListener.getRegexHandlers()) {
			if (handler.getName().equals(argument)) {
				return new MultiReplyResult(handler.getHelp());
			}
		}

		return new ErrorResult("Specified help command '" + argument + "' is unknown");
	}

	private IResult buildGeneralHelp() {
		List<String> messages = new ArrayList<>();
		messages.add("Known commands:");
		messages.addAll(getHelpCommandHandlers());
		messages.add("Other supported input:");
		messages.addAll(map(inputListener.getFactories()));
		messages.addAll(map(inputListener.getRegexHandlers()));
		return new MultiReplyResult(messages);
	}

	//@formatter:off
	private List<String> getHelpCommandHandlers() {
		return inputListener.getCommandHandlers().stream()
				.filter(handler -> handler.hasHelp())
				.flatMap(this::mapCommandHandler)
				.sorted()
				.collect(Collectors.toList());
	}

	private Stream<String> mapCommandHandler(ICommandHandler handler) {
		Stream<String> test = handler.getCommandNames().stream()
				.map(name -> name + " - " + handler.getSimpleHelp());
		return test;
	}

	private List<String> map(Collection<? extends IInputHandler> handlers) {
		return handlers.stream()
				.filter(handler -> handler.hasHelp())
				.map(handler -> handler.getName() + " - " + handler.getSimpleHelp())
				.sorted()
				.collect(Collectors.toList());
	}
	//@formatter:on
}
