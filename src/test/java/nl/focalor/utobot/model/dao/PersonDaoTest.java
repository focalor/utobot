package nl.focalor.utobot.model.dao;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.repository.PersonRepository;
import nl.focalor.utobot.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class PersonDaoTest {

	@Autowired
	private PersonRepository dao;

//	@Test
//	public void get() {
//		// Test
//		Person result = dao.findOne(1);
//
//		// Verify
//		assertNotNull(result);
//		assertEquals(l, result.getId().longValue());
//		assertEquals("jan", result.getName());
//
//	}
//
//	@Test
//	public void create() {
//		// Setup
//		Person person = new Person();
//		person.setName("piet");
//
//		// Test
//		dao.save(person);
//
//		// Verify
//		Person result = dao.findOne(person.getId());
//		assertNotNull(result);
//		assertEquals("piet", result.getName());
//
//	}
//
//	@Test
//	public void find() {
//		// Test
//		Set<Person> result = dao.find("jan", true);
//
//		// Verify
//		assertEquals(1, result.size());
//
//		Person person = result.iterator().next();
//		assertNotNull(person.getId());
//		assertEquals("jan", person.getName());
//	}
//
//	@Test
//	public void findPartial() {
//		// Test
//		Set<Person> result = dao.find("an", true);
//
//		// Verify
//		assertEquals(1, result.size());
//
//		Person person = result.iterator().next();
//		assertNotNull(person.getId());
//		assertEquals("jan", person.getName());
//	}
//
//	@Test
//	public void findNick() {
//		// Test
//		Set<Person> result = dao.find("klaassie", true);
//
//		// Verify
//		assertEquals(1, result.size());
//
//		Person person = result.iterator().next();
//		assertNotNull(person.getId());
//		assertEquals("klaas", person.getName());
//	}
//
//	@Test(expected = DuplicateKeyException.class)
//	public void uniqueName() {
//		// Setup
//		Person person = new Person();
//		person.setName("piet");
//
//		// Test
//		dao.create(person);
//		dao.create(person);
//	}

}
