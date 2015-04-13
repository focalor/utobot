package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractCommandHandler;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;
import nl.focalor.utobot.base.input.handler.HelpHandler;
import nl.focalor.utobot.base.input.listener.IInputListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author focalor
 */
@RunWith(MockitoJUnitRunner.class)
public class HelpHandlerTest {
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
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput.createFor("test", "!help"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(9, msgs.size());
		assertEquals("Known commands:", msgs.get(0));
		assertEquals("aTest - No help available", msgs.get(1));
		assertEquals("command - No help available", msgs.get(2));
		assertEquals("command2 - No help available", msgs.get(3));
		assertEquals("test - No help available", msgs.get(4));
		assertEquals("zTest - No help available", msgs.get(5));
		assertEquals("Other supported input:", msgs.get(6));
		assertEquals("regexA - No help available", msgs.get(7));
		assertEquals("regexB - No help available", msgs.get(8));
	}

	@Test
	public void specificHelpCommand() {
		// Test
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput
				.createFor("test", "!help zTest"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(2, msgs.size());
		assertEquals("Help for test", msgs.get(0));
		assertEquals("No help available", msgs.get(1));
	}

	@Test
	public void specificHelpRegex() {
		// Test
		MultiReplyResult result = (MultiReplyResult) handler.handleCommand(CommandInput.createFor("test",
				"!help regexB"));

		// Verify
		List<String> msgs = result.getMessages();
		assertEquals(3, msgs.size());
		assertEquals("Help for regexB", msgs.get(0));
		assertEquals("Following syntax is supported:", msgs.get(1));
		assertEquals("regex2", msgs.get(2));
	}

	private class CommandHandler extends AbstractCommandHandler {
		public CommandHandler(String commandName, String... alternateCommandNames) {
			super(commandName, alternateCommandNames);
		}

		@Override
		public IResult handleCommand(CommandInput event) {
			return null;
		}
	}

	private class RegexHandler extends AbstractRegexHandler {
		public RegexHandler(String name, String regex) {
			super(name, regex);
		}

		@Override
		public IResult handleInput(IInput input) {
			return null;
		}

	}
}
