package nl.focalor.utobot.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

		Set<Person> people = new HashSet<>();
		people.add(pers);
		when(personDao.find("naam", true)).thenReturn(people);

		Province prov = new Province();
		when(provService.find(1l, null, null)).thenReturn(Arrays.asList(prov));

		// Test
		Set<Person> result = service.load("naam", null, true);

		// Verify
		assertNotNull(result);
		assertTrue(1 == result.size());

		Person resultPerson = result.iterator().next();
		assertSame(pers, resultPerson);
		assertNotNull(resultPerson.getProvince());
		assertSame(prov, resultPerson.getProvince());
	}

	@Test
	public void loadPersonPostfix() {
		// Setup
		Person pers = new Person();
		pers.setName("naam");
		pers.setId(1l);

		Set<Person> people = new HashSet<>();
		people.add(pers);
		when(personDao.find("naam", true)).thenReturn(people);

		Province prov = new Province();
		when(provService.find(1l, null, null)).thenReturn(Arrays.asList(prov));

		// Test
		Set<Person> result = service.load("naam|zzz", null, true);

		// Verify
		assertNotNull(result);
		assertTrue(1 == result.size());

		Person resultPerson = result.iterator().next();
		assertSame(pers, resultPerson);
		assertNotNull(resultPerson.getProvince());
		assertSame(prov, resultPerson.getProvince());
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
		when(provService.find(null, "test", true)).thenReturn(Arrays.asList(prov));

		// Test
		Set<Person> result = service.load("naam", "test", true);

		// Verify
		assertTrue(1 == result.size());

		Person resultPerson = result.iterator().next();
		assertSame(pers, resultPerson);
		assertNotNull(resultPerson.getProvince());
		assertSame(prov, resultPerson.getProvince());
	}
}
