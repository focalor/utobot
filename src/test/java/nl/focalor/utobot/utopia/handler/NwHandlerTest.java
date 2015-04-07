package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.util.ReflectionUtil;
import org.junit.Test;

public class NwHandlerTest {
	private final NwHandler handler = new NwHandler();

	@Test
	public void event() {
		// Test
		IResult reply = handler.handleCommand(CommandInput.createFor("user", "!nw 15.73"));

		// Verify
		String msg = ReflectionUtil.getField(reply, "message", String.class);
		assertEquals("Networth interval: 12.58 - 18.88", msg);
	}
}
