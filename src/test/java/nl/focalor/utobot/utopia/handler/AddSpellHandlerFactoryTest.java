package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.ICommandHandler;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

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
public class AddSpellHandlerFactoryTest {
	@InjectMocks
	private final AddSpellHandlerFactory factory = new AddSpellHandlerFactory();

	@Mock
	private ISpellService spellService;
	@Mock
	private IUtopiaService utopiaService;
	@Mock
	private IPersonService personService;

	@Before
	public void init() {
		Spell spell = new Spell();
		spell.setId("L&P");
		spell.setName("Love and Peace");
		spell.setSelfSyntax("cast (\\d{1,2}) days");

		when(spellService.getKnownSpells()).thenReturn(Arrays.asList(spell));

		factory.init();
	}

	@Test
	public void commandHandlers() {
		// Test
		List<ICommandHandler> handlers = factory.getCommandHandlers();

		// verify
		assertEquals(0, handlers.size());
	}

	@Test
	public void regexHandlers() {
		// Test
		List<IRegexHandler> handlers = factory.getRegexHandlers();

		// verify
		assertEquals(1, handlers.size());

		IRegexHandler handler = handlers.get(0);
		assertEquals(false, handler.hasHelp());
	}

	@Test
	public void regexHandlerBadInput() {
		// Setup
		IRegexHandler handler = factory.getRegexHandlers().get(0);

		// Test
		IResult result = handler.handleInput(new Input("test", "cast A2 days"));

		// Verify
		assertTrue(result instanceof ErrorResult);
	}

	@Test
	public void regexHandlerUnknownPerson() {
		// Setup
		IRegexHandler handler = factory.getRegexHandlers().get(0);

		// Test
		ReplyResult result = (ReplyResult) handler.handleInput(new Input("test", "cast 1 days"));

		// Verify
		assertEquals("Unrecognized player, register your province/nick", result.getMessage());
	}

	@Test
	public void regexHandlerGoodInput() {
		// Setup
		IRegexHandler handler = factory.getRegexHandlers().get(0);
		Person person = new Person();
		person.setName("test");
		when(personService.find("test", true)).thenReturn(person);

		// Test
		ReplyResult result = (ReplyResult) handler.handleInput(new Input("test", "cast 1 days"));

		// Verify
		assertEquals("Love and Peace added for test for 1 hours", result.getMessage());
	}
}
