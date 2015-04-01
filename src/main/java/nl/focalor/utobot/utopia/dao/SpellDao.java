package nl.focalor.utobot.utopia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import nl.focalor.utobot.utopia.model.SpellCast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SpellDao implements ISpellDao {
	private final JdbcTemplate jdbcTemplate;
	private final SpellCastRowMapper mapper = new SpellCastRowMapper();

	@Autowired
	public SpellDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void create(SpellCast spellCast) {
		PreparedStatementCreator psc = con -> {
			PreparedStatement statement = con.prepareStatement(
					"INSERT INTO spellCasts(spellId, lastHour) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, spellCast.getSpellId());
			statement.setInt(2, spellCast.getLastHour());
			return statement;
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		spellCast.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM spellCasts WHERE id = ?",
				new Object[] { id }, new int[] { Types.INTEGER });
	}

	@Override
	public List<SpellCast> find() {
		return jdbcTemplate.query("SELECT * FROM spellCasts", mapper);
	}

	private class SpellCastRowMapper implements RowMapper<SpellCast> {

		@Override
		public SpellCast mapRow(ResultSet rs, int rowNum) throws SQLException {
			SpellCast result = new SpellCast();
			result.setId(rs.getLong("id"));
			result.setLastHour(rs.getInt("lastHour"));
			result.setSpellId(rs.getString("spellId"));
			return result;
		}
	}
}
