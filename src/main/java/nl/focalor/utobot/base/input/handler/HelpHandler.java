package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
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
		messages.addAll(getHelpAllCommandHandlers().sorted().collect(Collectors.toList()));
		messages.add("Other supported input:");
		messages.addAll(getHelpAllRegexHandlers().sorted().collect(Collectors.toList()));
		return new MultiReplyResult(messages);
	}

	private Stream<String> getHelpAllCommandHandlers() {
		return inputListener.getCommandHandlers().stream().flatMap(this::mapCommandHandler);
	}

	private Stream<String> mapCommandHandler(ICommandHandler handler) {
		return handler.getCommandNames().stream().map(name -> name + " - " + handler.getSimpleHelp());
	}

	private Stream<String> getHelpAllRegexHandlers() {
		return inputListener.getRegexHandlers().stream()
				.map(handler -> handler.getName() + " - " + handler.getSimpleHelp());
	}

}
