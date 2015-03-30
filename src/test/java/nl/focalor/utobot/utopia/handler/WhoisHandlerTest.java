package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.model.Person;
import nl.focalor.utobot.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.model.Race;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WhoisHandlerTest {
	@InjectMocks
	private WhoisHandler handler;

	@Mock
	private IPersonService personService;

	@Test
	public void multipleHits() {
		// Setup
		Person person1 = new Person();
		person1.setName("jan");
		Person person2 = new Person();
		person2.setName("jannie");

		Province prov = new Province();
		prov.setName("Prov");
		prov.setIsland(3);
		prov.setKingdom(2);
		prov.setRace(Race.FAERY);
		prov.setPersonality(Personality.TACTICIAN);
		person2.setProvince(prov);

		ArrayList<Person> people = new ArrayList<Person>();
		people.add(person1);
		people.add(person2);
		when(personService.loadPeople("jan", "jan")).thenReturn(people);

		// Test
		IResult res = handler.handleCommand(CommandInput
				.createFor("!whois jan"));

		// Verify
		assertTrue(res instanceof MultiReplyResult);

		List<String> messages = ((MultiReplyResult) res).getMessages();
		assertEquals(2, messages.size());
		assertEquals("jan", messages.get(0));
		assertEquals("jannie - Prov [Faery / Tactician]", messages.get(1));
	}
}
