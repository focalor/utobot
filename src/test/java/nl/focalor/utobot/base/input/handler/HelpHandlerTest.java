package nl.focalor.utobot.base.input.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.listener.IInputListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author focalor
 */
public class HelpHandlerTest extends AbstractHandlerTest {
	@InjectMocks
	private HelpHandler handler;

	@Mock
	private IInputListener listener;

	@Before
	public void init() {
		CommandHandler commandHandler1 = new CommandHandler("command", "command2");
		CommandHandler commandHandler2 = new CommandHandler("test", "aTest", "zTest");

		RegexHandler regexHandler1 = new RegexHandler("regexA", "regex1");
		RegexHandler regexHandler2 = new RegexHandler("regexB", "regex2");

		when(listener.getCommandHandlers()).thenReturn(new HashSet<>(Arrays.asList(commandHandler1, commandHandler2)));
		when(listener.getRegexHandlers()).thenReturn(new HashSet<>(Arrays.asList(regexHandler1, regexHandler2)));
	}

	@Test
	public void generalHelp() {
		// Test
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput.createFor(listener, null,
				"test", "!help"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(10, msgs.size());
		assertEquals("Type !help COMMAND for extra information on a command/other input", msgs.get(0));
		assertEquals("Known commands:", msgs.get(1));
		assertEquals("aTest - simple", msgs.get(2));
		assertEquals("command - simple", msgs.get(3));
		assertEquals("command2 - simple", msgs.get(4));
		assertEquals("test - simple", msgs.get(5));
		assertEquals("zTest - simple", msgs.get(6));
		assertEquals("Other supported input:", msgs.get(7));
		assertEquals("regexA - simple", msgs.get(8));
		assertEquals("regexB - simple", msgs.get(9));
	}

	@Test
	public void specificHelpCommand() {
		// Test
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput.createFor(listener, null,
				"test", "!help zTest"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(2, msgs.size());
		assertEquals("Help for test", msgs.get(0));
		assertEquals("big", msgs.get(1));
	}

	@Test
	public void specificHelpRegex() {
		// Test
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput.createFor(listener, null,
				"test", "!help regexB"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(2, msgs.size());
		assertEquals("Help for regexB", msgs.get(0));
		assertEquals("big", msgs.get(1));
	}

	@Override
	public IInputHandler getHandler() {
		return handler;
	}

	private class CommandHandler extends AbstractGenericCommandHandler {
		public CommandHandler(String commandName, String... alternateCommandNames) {
			super(commandName, alternateCommandNames);
		}

		@Override
		public IResult handleCommand(CommandInput event) {
			return NoReplyResult.NO_REPLY;
		}

		@Override
		public boolean hasHelp() {
			return true;
		}

		@Override
		public List<String> getHelpBody() {
			return Arrays.asList("big");
		}

		@Override
		public String getSimpleHelp() {
			return "simple";
		}
	}

	private class RegexHandler extends AbstractRegexHandler {
		public RegexHandler(String name, String regex) {
			super(name, regex);
		}

		@Override
		public IResult handleInput(Matcher matcher, IInput input) {
			return null;
		}

		@Override
		public boolean hasHelp() {
			return true;
		}

		@Override
		public List<String> getHelpBody() {
			return Arrays.asList("big");
		}

		@Override
		public String getSimpleHelp() {
			return "simple";
		}
	}

}
