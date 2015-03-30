package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttackHandlerTest {
	@InjectMocks
	private AttackHandler handler;

	@Mock
	private IAttackService service;

	@Test
	public void matches() {
		// Setup
		Input input = new Input(
				"recaptured 35 acres from our enemy! Taking full control of our new land will take 9.11 days. The new land wi");

		// Test
		boolean result = handler.matches(input);

		// Verify
		assertTrue(result);
	}

	@Test
	public void handle() {
		// Setup
		Input input = new Input(
				"recaptured 35 acres from our enemy! Taking full control of our new land will take 9.11 days. The new land wi");

		// Test
		IResult result = handler.handleInput(input);

		// Verify
		assertNotNull(result);
		verify(service).add(any(Attack.class));
		verifyNoMoreInteractions(service);
	}
}
