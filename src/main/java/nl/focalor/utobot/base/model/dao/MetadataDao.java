package nl.focalor.utobot.base.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import nl.focalor.utobot.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MetadataDao implements IMetadataDao {
	private static final VersionMapper versionMapper = new VersionMapper();
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public MetadataDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public Version getSchemaVersion() {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT version_major, version_minor, version_patch FROM schema_information", new HashMap<>(),
					versionMapper);
		} catch (BadSqlGrammarException | EmptyResultDataAccessException ex) {
			return new Version(0, 0, 0);
		}
	}

	private static class VersionMapper implements RowMapper<Version> {

		@Override
		public Version mapRow(ResultSet rs, int rowNum) throws SQLException {
			int major = rs.getInt("version_major");
			int minor = rs.getInt("version_minor");
			int patch = rs.getInt("version_patch");
			return new Version(major, minor, patch);
		}

	}
}
