package nl.focalor.utobot.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import nl.focalor.utobot.base.model.Person;
import nl.focalor.utobot.base.model.dao.IPersonDao;
import nl.focalor.utobot.base.model.service.PersonService;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.service.IProvinceService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {
	@InjectMocks
	private PersonService service;

	@Mock
	private IPersonDao personDao;
	@Mock
	private IProvinceService provService;

	@Test
	public void loadPerson() {
		// Setup
		Person pers = new Person();
		pers.setName("naam");
		pers.setId(1l);
		List<Person> people = Arrays.asList(pers);
		when(personDao.find("naam")).thenReturn(people);

		Province prov = new Province();
		when(provService.find(1l, null)).thenReturn(Arrays.asList(prov));

		// Test
		List<Person> result = service.loadPeople("naam", null);

		// Verify
		assertNotNull(result);
		assertTrue(1 == result.size());
		assertSame(people.get(0), result.get(0));
		assertNotNull(result.get(0).getProvince());
		assertSame(prov, result.get(0).getProvince());
	}

	@Test
	public void loadPersonByProvName() {
		// Setup
		Person pers = new Person();
		pers.setName("naam");
		pers.setId(12l);
		when(personDao.get(12l)).thenReturn(pers);

		Province prov = new Province();
		prov.setPersonId(12l);
		when(provService.find(null, "test")).thenReturn(Arrays.asList(prov));

		// Test
		List<Person> result = service.loadPeople("naam", "test");

		// Verify
		assertTrue(1 == result.size());
		assertSame(pers, result.get(0));
		assertNotNull(result.get(0).getProvince());
		assertSame(prov, result.get(0).getProvince());
	}
}
