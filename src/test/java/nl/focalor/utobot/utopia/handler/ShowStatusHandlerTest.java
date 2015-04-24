package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.IInputHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.util.ReflectionUtil;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author focalor
 */
public class ShowStatusHandlerTest extends AbstractUtoHandlerTest {

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

	private SpellCast cast;

	@Before
	public void init() {
		Calendar cal = Calendar.getInstance();
		cal.clear();

		Person person = new Person();
		person.setName("joop");
		when(personService.find("joop", false)).thenReturn(person);

		Attack attack1 = new Attack();
		cal.set(2010, 1, 1);
		attack1.setReturnDate(cal.getTime());
		attack1.setPerson(person);

		Attack attack2 = new Attack();
		cal.set(2009, 1, 1);
		attack2.setReturnDate(cal.getTime());
		attack2.setPerson(person);
		when(attackService.findByPerson(person)).thenReturn(Arrays.asList(attack1, attack2));

		cast = new SpellCast();
		when(spellService.findByCaster(person)).thenReturn(Arrays.asList(cast));

		when(utopiaService.getHourOfAge()).thenReturn(100);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void simple() {
		// Setup
		cast.setLastHour(100);
		cast.setSpellId("L&P");

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

	@Test
	@SuppressWarnings("unchecked")
	public void multipleHour() {
		// Setup
		cast.setLastHour(110);
		cast.setSpellId("L&P");

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
		assertEquals("L&P ends in 11 days", messages.get(5));
	}

	@Test
	public void unknownPerson() {
		// Setup
		cast.setLastHour(110);
		cast.setSpellId("L&P");

		// Test
		IResult reply = handler.handleCommand(CommandInput.createFor("joop", "!status piet"));

		// Verify
		assertTrue(reply instanceof ErrorResult);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void differentPerson() {
		// Setup
		Person person = new Person();
		person.setName("piet");
		Province prov = new Province();
		person.setProvince(prov);
		when(personService.find("piet", false)).thenReturn(person);

		cast.setLastHour(110);
		cast.setSpellId("L&P");

		// Test
		IResult reply = handler.handleCommand(CommandInput.createFor("joop", "!status piet"));

		// Verify
		List<String> messages = ReflectionUtil.getField(reply, "messages", List.class);
		assertEquals(3, messages.size());
		assertEquals("Status for piet", messages.get(0));
		assertEquals("Armies out:", messages.get(1));
		assertEquals("Active spells:", messages.get(2));
	}

	@Override
	public IInputHandler getHandler() {
		return handler;
	}
}
