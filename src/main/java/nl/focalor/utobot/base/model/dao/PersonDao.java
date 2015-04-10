package nl.focalor.utobot.base.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.focalor.utobot.base.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonDao implements IPersonDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final PersonRowMapper mapper = new PersonRowMapper();

	@Autowired
	public PersonDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void create(Person person) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", person.getName());

		jdbcTemplate.update("INSERT INTO people(name) VALUES (:name)", params, keyHolder);

		person.setId(keyHolder.getKey().longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public Person get(long id) {
		try {
			Map<String, Object> params = Collections.singletonMap("id", id);
			return jdbcTemplate.queryForObject("SELECT * FROM people WHERE id=:id", params, mapper);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	@Transactional
	public void addNick(long personId, String nick) {
		Map<String, Object> params = new HashMap<>();
		params.put("personId", personId);
		params.put("nick", nick);
		jdbcTemplate.update("INSERT INTO nicks(personId, nick) VALUES (:personId, :nick)", params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> find() {
		return jdbcTemplate.query("SELECT * FROM people", mapper);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Person> find(String name, boolean fuzzy) {

		Set<Person> result = new HashSet<>();
		result.addAll(findByName(name, fuzzy));
		result.addAll(findByNick(name, fuzzy));
		return result;
	}

	private List<Person> findByName(String name, boolean fuzzy) {
		if (fuzzy) {
			StringBuilder argument = new StringBuilder();
			argument.append('%');
			argument.append(name);
			argument.append('%');

			Map<String, Object> params = Collections.singletonMap("name", argument.toString());
			return jdbcTemplate.query("SELECT * FROM people WHERE LOWER(name) LIKE LOWER(:name)", params, mapper);
		} else {
			Map<String, Object> params = Collections.singletonMap("name", name);
			return jdbcTemplate.query("SELECT * FROM people WHERE LOWER(name) = LOWER(:name)", params, mapper);
		}
	}

	private List<Person> findByNick(String nick, boolean fuzzy) {
		if (fuzzy) {
			StringBuilder argument = new StringBuilder();
			argument.append('%');
			argument.append(nick);
			argument.append('%');

			Map<String, Object> params = Collections.singletonMap("nick", argument.toString());

			return jdbcTemplate
					.query("SELECT people.* FROM people INNER JOIN nicks ON people.id = nicks.personId WHERE LOWER(nick) LIKE LOWER(:nick)",
							params, mapper);
		} else {
			Map<String, Object> params = Collections.singletonMap("nick", nick);
			return jdbcTemplate
					.query("SELECT people.* FROM people INNER JOIN nicks ON people.id = nicks.personId WHERE LOWER(nick) = LOWER(:nick)",
							params, mapper);
		}
	}

	private class PersonRowMapper implements RowMapper<Person> {

		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			Person result = new Person();
			result.setId(rs.getLong("id"));
			result.setName(rs.getString("name"));
			return result;
		}

	}
}
