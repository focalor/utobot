package nl.focalor.utobot.irc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nl.focalor.utobot.base.input.CommandInput;

import org.junit.Test;

public class CommandTest {
	@Test
	public void parseNoArgs() {
		// Test
		CommandInput command = CommandInput.createFor("!alert");

		// Verify
		assertEquals("alert", command.getCommand());
		assertNull(command.getArgument());
	}

	@Test
	public void parseBlankArgs() {
		// Test
		CommandInput command = CommandInput.createFor("!alert  \t");

		// Verify
		assertEquals("alert", command.getCommand());
		assertEquals("", command.getArgument());
	}

	@Test
	public void parseArgs() {
		// Test
		CommandInput command = CommandInput.createFor("!alert  test  test2 \t");

		// Verify
		assertEquals("alert", command.getCommand());
		assertEquals("test  test2 \t", command.getArgument());
	}
}
