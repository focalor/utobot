package nl.focalor.utobot.base.input.handler;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author focalor
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractHandlerTest {

	public abstract IInputHandler getHandler();

	@Test
	public void handlerWithHelpSimpleHelp() {
		IInputHandler handler = getHandler();
		if (handler.hasHelp()) {
			// Test
			String simpleHelp = getHandler().getSimpleHelp();

			// Verify
			assertTrue(simpleHelp.length() > 0);
		}
	}

	@Test
	public void handlerWithoutHelpSimpleHelp() {
		IInputHandler handler = getHandler();
		if (!handler.hasHelp()) {
			// Test
			String simpleHelp = getHandler().getSimpleHelp();

			// Verify
			assertNull(simpleHelp);
		}
	}

	@Test
	public void handlerWithHelpHelpBody() {
		IInputHandler handler = getHandler();
		if (handler.hasHelp()) {
			// Test
			List<String> body = handler.getHelpBody();

			// Verify
			assertTrue(body.size() > 0);
			for (String str : body) {
				assertTrue(str.length() > 0);
			}
		}
	}

	@Test
	public void handlerWithoutHelpHelpBody() {
		IInputHandler handler = getHandler();
		if (!handler.hasHelp()) {
			// Test
			List<String> body = handler.getHelpBody();

			// Verify
			assertNull(body);
		}
	}
}
