package nl.focalor.utobot.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import nl.focalor.utobot.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonDao implements IPersonDao {
	private final JdbcTemplate jdbcTemplate;
	private final PersonRowMapper mapper = new PersonRowMapper();

	@Autowired
	public PersonDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void create(Person person) {
		PreparedStatementCreator psc = con -> {
			PreparedStatement statement = con.prepareStatement(
					"INSERT INTO people(name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, person.getName());
			return statement;
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		person.setId(keyHolder.getKey().longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public Person get(long id) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * FROM people WHERE id=?", new Object[] { id },
					new int[] { Types.INTEGER }, mapper);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> find(String namePart) {
		StringBuilder argument = new StringBuilder();
		argument.append('%');
		argument.append(namePart);
		argument.append('%');

		return jdbcTemplate.query(
				"SELECT * FROM people WHERE LOWER(name) LIKE LOWER(?)",
				new Object[] { argument.toString() },
				new int[] { Types.VARCHAR }, mapper);
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
