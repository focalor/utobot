package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.handler.IInputHandler;
import nl.focalor.utobot.base.model.entity.Nick;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Race;
import nl.focalor.utobot.utopia.model.entity.Province;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class WhoisHandlerTest extends AbstractUtoHandlerTest {
	@InjectMocks
	private WhoisHandler handler;

	@Mock
	private IPersonService personService;

	@Test
	public void multipleHits() {
		// Setup
		Person person1 = new Person();
		person1.setName("jan");
		person1.setNicks(new ArrayList<>(0));
		Person person2 = new Person();
		person2.setName("jannie");
		List<Nick> nicks = new ArrayList<>();
		nicks.add(new Nick());
		nicks.add(new Nick());
		nicks.get(0).setNick("jannie1");
		nicks.get(1).setNick("jannie2");
		person2.setNicks(nicks);

		Province prov = new Province();
		prov.setName("Prov");
		prov.setIsland(3);
		prov.setKingdom(2);
		prov.setRace(Race.FAERY);
		prov.setPersonality(Personality.TACTICIAN);
		person2.setProvince(prov);

		List<Person> people = new ArrayList<>();
		people.add(person1);
		people.add(person2);
		when(personService.findByNickNameOrProvince("jan")).thenReturn(people);

		// Test
		IResult res = handler.handleCommand(CommandInput.createFor(null, null, "user", "!whois jan"));

		// Verify
		assertTrue(res instanceof MultiReplyResult);

		List<String> messages = ((MultiReplyResult) res).getMessages();
		assertEquals(6, messages.size());
		assertEquals("jan", messages.get(0));
		assertEquals("Known nicknames:", messages.get(1));
		assertEquals("", messages.get(2));
		assertEquals("jannie - Prov [Faery / Tactician]", messages.get(3));
		assertEquals("Known nicknames:", messages.get(4));
		assertEquals("jannie1, jannie2", messages.get(5));
	}

	@Override
	public IInputHandler getHandler() {
		return handler;
	}
}
