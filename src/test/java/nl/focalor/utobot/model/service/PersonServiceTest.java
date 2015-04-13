package nl.focalor.utobot.model.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.repository.PersonRepository;
import nl.focalor.utobot.base.model.service.PersonService;
import nl.focalor.utobot.utopia.model.entity.Province;
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
	private PersonRepository personDao;
	@Mock
	private IProvinceService provService;

	@Test
	public void loadPerson() {
		// Setup
		Person pers = new Person();
		pers.setName("naam");
		pers.setId(1l);
		Province prov = new Province();
		pers.setProvince(prov);

		List<Person> people = new ArrayList<>();
		people.add(pers);
		when(personDao.findByNameOrNick("naam")).thenReturn(people);

		when(provService.find(1l)).thenReturn(prov);

		// Test
		List<Person> result = personDao.findByNameOrNick("naam");

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

		Province prov = new Province();
		prov.setOwner(pers);
		pers.setProvince(prov);

		List<Person> people = new ArrayList<Person>();
		people.add(pers);

		when(personDao.findOne(12l)).thenReturn(pers);
		when(service.findByNickNameOrProvince("test")).thenReturn(people);

		// Test
		List<Person> result = service.findByNickNameOrProvince("test");

		// Verify
		assertTrue(1 == result.size());

		Person resultPerson = result.iterator().next();
		assertSame(pers, resultPerson);
		assertNotNull(resultPerson.getProvince());
		assertSame(prov, resultPerson.getProvince());
	}
}
