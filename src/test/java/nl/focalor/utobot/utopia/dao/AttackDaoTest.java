package nl.focalor.utobot.utopia.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Date;
import java.util.List;
import nl.focalor.utobot.config.TestConfig;
import nl.focalor.utobot.utopia.model.Attack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AttackDaoTest {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private AttackDao dao;

	@Before
	public void init() {
		dao = new AttackDao(jdbcTemplate);
	}

	@Test
	public void get() {
		// Test
		Attack result = dao.get(-1l);

		// Verify
		assertNotNull(result);
		assertEquals(-1l, result.getId().longValue());
		assertEquals("jan", result.getPerson());
		assertEquals(-1l, result.getPersonId().longValue());
	}

	@Test
	public void create() {
		// Setup
		Attack insert = new Attack();
		insert.setPersonId(-1l);
		insert.setPerson("test");
		Date date = new Date();
		insert.setReturnDate(date);

		// Test
		dao.create(insert);

		// Verify
		assertNotNull(insert.getId());

		Attack fromDb = dao.get(insert.getId());
		assertEquals(insert.getId().longValue(), fromDb.getId().longValue());
		assertEquals("test", fromDb.getPerson());
		assertEquals(-1l, fromDb.getPersonId().longValue());
		assertEquals(date, fromDb.getReturnDate());
	}

	@Test
	public void delete() {
		// Test
		dao.delete(-1l);

		// Verify
		assertNull(dao.get(-1l));
	}

	@Test
	public void find() {
		// Test
		List<Attack> findAttacks = dao.find(null, null);

		// Verify
		assertEquals(1, findAttacks.size());
		assertEquals("jan", findAttacks.get(0).getPerson());
	}
}
