package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.util.ReflectionUtil;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author focalor
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowStatusHandlerTest {

	@InjectMocks
	private ShowStatusHandler handler;

	@Mock
	private IPersonService personService;
	@Mock
	private IAttackService attackService;
	@Mock
	private ISpellService spellService;
	@Mock
	private IUtopiaService utopiaService;

	@Test
	@SuppressWarnings("unchecked")
	public void event() {
		// Setup
		Calendar cal = Calendar.getInstance();
		cal.clear();

		Attack attack1 = new Attack();
		cal.set(2010, 1, 1);
		attack1.setReturnDate(cal.getTime());
		attack1.setPerson("joop");

		Attack attack2 = new Attack();
		cal.set(2009, 1, 1);
		attack2.setReturnDate(cal.getTime());
		attack2.setPerson("joop");
		when(attackService.find(null, "joop")).thenReturn(Arrays.asList(attack1, attack2));

		SpellCast cast = new SpellCast();
		cast.setLastHour(100);
		cast.setSpellId("L&P");
		when(spellService.find(null, "joop")).thenReturn(Arrays.asList(cast));

		when(utopiaService.getHourOfAge()).thenReturn(100);

		// Test
		IResult reply = handler.handleCommand(CommandInput.createFor("joop", "!status"));

		// Verify
		List<String> messages = ReflectionUtil.getField(reply, "messages", List.class);
		assertEquals(6, messages.size());
		assertEquals("Status for joop", messages.get(0));
		assertEquals("Armies out:", messages.get(1));
		assertTrue(messages.get(2).startsWith("Army out for"));
		assertTrue(messages.get(3).startsWith("Army out for"));
		assertEquals("Active spells:", messages.get(4));
		assertEquals("L&P ends in 1 day", messages.get(5));
	}
}
