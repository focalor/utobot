package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.IInputHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author focalor
 */
public class ArmiesHandlerTest extends AbstractUtoHandlerTest {

	@InjectMocks
	private ArmiesHandler handler;
	@Mock
	private IAttackService attackService;

	@Test
	public void noArmies() {
		// Test
		IResult result = handler.handleCommand(CommandInput.createFor("user", "!armies"));

		// Verify
		assertTrue(result instanceof ReplyResult);

		ReplyResult reply = (ReplyResult) result;
		assertEquals("No armies found", reply.getMessage());
	}

	@Test
	public void multipleArmies() {
		// Setup
		List<Attack> armies = new ArrayList<>();
		armies.add(buildAttack("jan", new DateTime().plusHours(1).plusMillis(500)));
		armies.add(buildAttack("klaas", new DateTime().plusHours(3).plusMillis(500)));
		when(attackService.findAll()).thenReturn(armies);

		// Test
		IResult result = handler.handleCommand(CommandInput.createFor("user", "!armies"));

		// Verify
		assertTrue(result instanceof MultiReplyResult);

		MultiReplyResult reply = (MultiReplyResult) result;
		assertEquals(2, reply.getMessages().size());

		assertEquals("jan's army is out for 1h 0m 0s", reply.getMessages().get(0));
		assertEquals("klaas's army is out for 3h 0m 0s", reply.getMessages().get(1));
	}

	private Attack buildAttack(String personName, DateTime returnDate) {
		Person person = new Person();
		person.setName(personName);

		Attack attack = new Attack();
		attack.setPerson(person);
		attack.setReturnDate(returnDate.toDate());

		return attack;
	}

	@Override
	public IInputHandler getHandler() {
		return handler;
	}
}
