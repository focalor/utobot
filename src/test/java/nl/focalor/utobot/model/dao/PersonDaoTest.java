package nl.focalor.utobot.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import nl.focalor.utobot.config.TestConfig;
import nl.focalor.utobot.model.Person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class PersonDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private PersonDao dao;

	@Before
	public void init() {
		dao = new PersonDao(jdbcTemplate);
	}

	@Test
	public void get() {
		// Test
		Person result = dao.get(-1);

		// Verify
		assertNotNull(result);
		assertEquals(-1l, result.getId().longValue());
		assertEquals("jan", result.getName());

	}

	@Test
	public void create() {
		// Setup
		Person person = new Person();
		person.setName("piet");

		// Test
		dao.create(person);

		// Verify
		Person result = dao.get(person.getId());
		assertNotNull(result);
		assertEquals("piet", result.getName());

	}

	@Test
	public void find() {
		// Test
		List<Person> result = dao.find("jan");

		// Verify
		assertEquals(1, result.size());
		assertNotNull(result.get(0).getId());
		assertEquals("jan", result.get(0).getName());
	}

	@Test
	public void findPartial() {
		// Test
		List<Person> result = dao.find("an");

		// Verify
		assertEquals(1, result.size());
		assertNotNull(result.get(0).getId());
		assertEquals("jan", result.get(0).getName());
	}

	@Test(expected = DuplicateKeyException.class)
	public void uniqueName() {
		// Setup
		Person person = new Person();
		person.setName("piet");

		// Test
		dao.create(person);
		dao.create(person);
	}

}
