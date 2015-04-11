package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.service.IProvinceService;
import org.junit.runner.RunWith;
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

//	@Test
//	public void test() {
//		// Test
//		IResult result = handler.handleCommand(CommandInput.createFor("user", "!addprov focalor - Foc [Elf/Mystic]"));
//
//		// Verify
//		ArgumentCaptor<Person> peopleCaptor = ArgumentCaptor.forClass(Person.class);
//		ArgumentCaptor<Province> provCaptor = ArgumentCaptor.forClass(Province.class);
//
//		String msg = ((ReplyResult) result).getMessage();
//		assertEquals("Province added", msg);
//		verify(personService).create(peopleCaptor.capture());
//		verify(provinceService).create(provCaptor.capture());
//		verifyNoMoreInteractions(personService, provinceService);
//
//		Person person = peopleCaptor.getValue();
//		Province prov = provCaptor.getValue();
//
//		assertEquals("focalor", person.getName());
//		assertEquals("Foc", prov.getName());
//		assertEquals(Race.ELF, prov.getRace());
//		assertEquals(Personality.MYSTIC, prov.getPersonality());
//	}
}
