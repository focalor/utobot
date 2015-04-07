package nl.focalor.utobot.utopia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.focalor.utobot.utopia.model.SpellCast;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SpellCastDao implements ISpellCastDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SpellCastRowMapper mapper = new SpellCastRowMapper();

	@Autowired
	public SpellCastDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void create(SpellCast spellCast) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("spellId", spellCast.getSpellId());
		params.addValue("lastHour", spellCast.getLastHour());
		params.addValue("personId", spellCast.getPersonId());
		params.addValue("person", spellCast.getPerson());

		jdbcTemplate
				.update("INSERT INTO spellCasts(spellId, lastHour, personId, person) VALUES (:spellId, :lastHour, :personId, :person)",
						params, keyHolder);

		spellCast.setId(keyHolder.getKey().longValue());
	}

	@Override
	@Transactional
	public void delete(long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		jdbcTemplate.update("DELETE FROM spellCasts WHERE id = ? :id", params);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SpellCast> find(Long personId, String person) {
		List<String> whereClause = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();

		if (personId != null) {
			whereClause.add("personId = :personId");
			params.put("personId", personId);
		}
		if (!StringUtils.isEmpty(person)) {
			whereClause.add("person = :person");
			params.put("person", person);
		}

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM spellCasts");
		if (!whereClause.isEmpty()) {
			query.append(" WHERE ");
			query.append(StringUtils.join(whereClause, " AND "));
		}
		return jdbcTemplate.query(query.toString(), params, mapper);
	}

	private class SpellCastRowMapper implements RowMapper<SpellCast> {

		@Override
		public SpellCast mapRow(ResultSet rs, int rowNum) throws SQLException {
			SpellCast result = new SpellCast();
			result.setId(rs.getLong("id"));
			result.setPerson(rs.getString("person"));
			result.setPersonId(rs.getLong("personId"));
			if (rs.wasNull()) {
				result.setPersonId(null);
			}
			result.setLastHour(rs.getInt("lastHour"));
			result.setSpellId(rs.getString("spellId"));
			return result;
		}
	}
}
