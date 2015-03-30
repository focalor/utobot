package nl.focalor.utobot.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import nl.focalor.utobot.config.TestConfig;
import nl.focalor.utobot.utopia.dao.ProvinceDao;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.model.Race;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ProvinceDaoTest {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private ProvinceDao dao;

	@Before
	public void init() {
		dao = new ProvinceDao(jdbcTemplate);
	}

	@Test
	public void get() {
		// Test
		Province result = dao.get(-2);

		// Verify
		assertNotNull(result);
		assertEquals("prov", result.getName());
		assertEquals(15, result.getIsland().intValue());
		assertEquals(2, result.getKingdom().intValue());
		assertEquals(-2l, result.getId().longValue());
		assertEquals(-1l, result.getPersonId().longValue());
		assertEquals(Race.DWARF, result.getRace());
		assertEquals(Personality.ROGUE, result.getPersonality());
	}

	@Test
	public void create() {
		// Setup
		Province prov = new Province();
		prov.setIsland(3);
		prov.setKingdom(5);
		prov.setName("province");
		prov.setPersonId(-1l);
		prov.setRace(Race.ELF);
		prov.setPersonality(Personality.MYSTIC);

		// Test
		dao.create(prov);

		// Verify
		assertNotNull(prov.getId());

		Province inserted = dao.get(prov.getId());
		assertEquals("province", inserted.getName());
		assertEquals(3, inserted.getIsland().intValue());
		assertEquals(5, inserted.getKingdom().intValue());
		assertEquals(-1l, inserted.getPersonId().longValue());
		assertEquals(Race.ELF, inserted.getRace());
		assertEquals(Personality.MYSTIC, inserted.getPersonality());
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void createInvalidPersonId() {
		// Setup
		Province prov = new Province();
		prov.setIsland(3);
		prov.setKingdom(5);
		prov.setName("province");
		prov.setPersonId(-189l);
		prov.setRace(Race.ELF);
		prov.setPersonality(Personality.MYSTIC);

		// Test
		dao.create(prov);
	}

	@Test
	public void find() {
		// Test
		List<Province> results = dao.find(-1l, null);

		// Verify
		assertEquals(1, results.size());

		Province result = results.get(0);
		assertEquals("prov", result.getName());
		assertEquals(15, result.getIsland().intValue());
		assertEquals(2, result.getKingdom().intValue());
		assertEquals(-2l, result.getId().longValue());
		assertEquals(-1l, result.getPersonId().longValue());
		assertEquals(Race.DWARF, result.getRace());
		assertEquals(Personality.ROGUE, result.getPersonality());
	}
}
