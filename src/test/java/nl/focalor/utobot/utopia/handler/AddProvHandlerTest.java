package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.model.Race;
import nl.focalor.utobot.utopia.service.IProvinceService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddProvHandlerTest {
	@InjectMocks
	private AddProvHandler handler;
	@Mock
	private IPersonService personService;
	@Mock
	private IProvinceService provinceService;

	@Test
	public void test() {
		// Test
		IResult result = handler.handleCommand(CommandInput.createFor("user",
				"!addprov focalor - Foc [Elf/Mystic]"));

		// Verify
		ArgumentCaptor<Person> peopleCaptor = ArgumentCaptor
				.forClass(Person.class);
		ArgumentCaptor<Province> provCaptor = ArgumentCaptor
				.forClass(Province.class);

		String msg = ((ReplyResult) result).getMessage();
		assertEquals("Province added", msg);
		verify(personService).create(peopleCaptor.capture());
		verify(provinceService).create(provCaptor.capture());
		verifyNoMoreInteractions(personService, provinceService);

		Person person = peopleCaptor.getValue();
		Province prov = provCaptor.getValue();

		assertEquals("focalor", person.getName());
		assertEquals("Foc", prov.getName());
		assertEquals(Race.ELF, prov.getRace());
		assertEquals(Personality.MYSTIC, prov.getPersonality());
	}
}
