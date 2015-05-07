package nl.focalor.utobot.base.input;

import nl.focalor.utobot.base.input.listener.IInputListener;
import org.apache.commons.lang3.StringUtils;

public class CommandInput extends Input {
	public static final char COMMAND_PREFIX = '!';

	private String command;
	private String argument;

	private CommandInput(IInputListener dispatcher, String room, String user, String input) {
		super(dispatcher, room, user, input);
	}

	public static CommandInput createFor(IInputListener dispatcher, String room, String user, String input) {
		if (input.charAt(0) != COMMAND_PREFIX) {
			throw new IllegalStateException("Input must start with " + COMMAND_PREFIX);
		}
		CommandInput result = new CommandInput(dispatcher, room, user, input);

		for (int i = 1; i < input.length(); i++) {
			if (Character.isWhitespace(input.charAt(i))) {
				result.setCommand(input.substring(1, i));
				result.setArgument(StringUtils.stripStart(input.substring(i), null));
				return result;
			}
		}
		// No arguments
		result.setCommand(input.substring(1));

		return result;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}
}
