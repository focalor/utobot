package nl.focalor.utobot.utopia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.model.Race;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProvinceDao implements IProvinceDao {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final ProvinceRowMapper mapper = new ProvinceRowMapper();

	@Autowired
	public ProvinceDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Province> find(Long personId, String namePart, Boolean fuzzy) {
		Map<String, Object> params = new HashMap<>();
		List<String> whereClauses = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM provinces");

		if (personId != null) {
			whereClauses.add("personId=:personId");
			params.put("personId", personId);
		}
		if (!StringUtils.isEmpty(namePart)) {
			if (fuzzy == null || fuzzy) {
				whereClauses.add("LOWER(name) LIKE LOWER(:name)");
				params.put("name", '%' + namePart + '%');
			} else {
				whereClauses.add("LOWER(name) = LOWER(:name)");
				params.put("name", namePart);
			}
		}

		if (!whereClauses.isEmpty()) {
			query.append(" WHERE ");
			query.append(StringUtils.join(whereClauses, " AND "));
		}

		return jdbcTemplate.query(query.toString(), params, mapper);
	}

	@Override
	@Transactional
	public void create(Province province) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("personId", province.getPersonId());
		params.addValue("name", province.getName());
		params.addValue("kingdom", province.getKingdom());
		params.addValue("island", province.getIsland());
		params.addValue("raceId", province.getRace().getId());
		params.addValue("personalityId", province.getPersonality().getId());

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate
				.update("INSERT INTO provinces(personId, name, kingdom, island, race, personality) VALUES (:personId, :name, :kingdom, :island, :raceId, :personalityId)",
						params, keyHolder);

		province.setId(keyHolder.getKey().longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public Province get(long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbcTemplate.queryForObject("SELECT * FROM provinces WHERE id=:id", params, mapper);
	}

	private static class ProvinceRowMapper implements RowMapper<Province> {

		@Override
		public Province mapRow(ResultSet rs, int rowNum) throws SQLException {
			Province result = new Province();
			result.setId(rs.getLong("id"));
			result.setIsland(rs.getInt("island"));
			result.setKingdom(rs.getInt("kingdom"));
			result.setName(rs.getString("name"));
			result.setPersonId(rs.getLong("personId"));
			result.setRace(Race.valueOf(rs.getInt("race")));
			result.setPersonality(Personality.valueOf(rs.getInt("personality")));
			return result;
		}
	}
}
