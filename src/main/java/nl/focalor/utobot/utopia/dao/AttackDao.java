package nl.focalor.utobot.utopia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import nl.focalor.utobot.utopia.model.Attack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AttackDao implements IAttackDao {
	private final JdbcTemplate jdbcTemplate;
	private final AttackRowMapper mapper = new AttackRowMapper();

	@Autowired
	public AttackDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public Attack get(long id) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * FROM attacks WHERE id=?", new Object[] { id },
					new int[] { Types.INTEGER }, mapper);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public void create(Attack attack) {
		PreparedStatementCreator psc = con -> {
			PreparedStatement statement = con
					.prepareStatement(
							"INSERT INTO attacks(person, personId, returnDate) VALUES (?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, attack.getPerson());
			if (attack.getPersonId() == null) {
				statement.setNull(2, Types.BIGINT);
			} else {
				statement.setLong(2, attack.getPersonId());
			}
			statement.setTimestamp(3, new Timestamp(attack.getReturnDate()
					.getTime()));
			return statement;
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		attack.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM attacks WHERE id = ?",
				new Object[] { id }, new int[] { Types.INTEGER });
	}

	@Override
	public List<Attack> find() {
		return jdbcTemplate.query("SELECT * FROM attacks", mapper);
	}

	private static class AttackRowMapper implements RowMapper<Attack> {

		@Override
		public Attack mapRow(ResultSet rs, int rowNum) throws SQLException {
			Attack result = new Attack();
			result.setId(rs.getLong("id"));
			result.setPerson(rs.getString("person"));
			result.setPersonId(rs.getLong("personId"));
			result.setReturnDate(rs.getTimestamp("returnDate"));
			return result;
		}
	}

}
