package nl.focalor.utobot.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.focalor.utobot.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MetadataDao implements IMetadataDao {
	private static final VersionMapper versionMapper = new VersionMapper();
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MetadataDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(readOnly = true)
	public Version getSchemaVersion() {
		try {
			return jdbcTemplate
					.queryForObject(
							"SELECT version_major, version_minor, version_patch FROM schema_information",
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
