package nl.focalor.utobot.model.dao;

import java.sql.Types;

import nl.focalor.utobot.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseMigrator implements IDatabaseMigrator {
	private final Version V0_0_1 = new Version(0, 0, 1);
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DatabaseMigrator(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Version migrateFromVersion(Version version) {
		if (version.compareTo(V0_0_1) < 0) {
			version = upgradeToV_0_0_1();
		}

		jdbcTemplate
				.update("UPDATE schema_information SET version_major=?, version_minor=?, version_patch=?",
						new Object[] { version.getMajor(), version.getMinor(),
								version.getPatch() }, new int[] {
								Types.INTEGER, Types.INTEGER, Types.INTEGER });
		return version;
	}

	private Version upgradeToV_0_0_1() {
		// Store general schema info
		jdbcTemplate
				.execute("CREATE TABLE schema_information (lock char(1) NOT NULL DEFAULT 'X', version_major INT NOT NULL, version_minor INT NOT NULL, version_patch INT NOT NULL, "
						+ "constraint PK_T1 PRIMARY KEY (Lock), constraint CK_T1_Locked CHECK (lock='X') )");
		jdbcTemplate
				.update("INSERT INTO schema_information(version_major, version_minor, version_patch) VALUES(0, 0, 1)");

		jdbcTemplate
				.execute("CREATE TABLE people (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL)");
		jdbcTemplate
				.execute("CREATE TABLE provinces (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, personId INT NOT NULL, name VARCHAR(50) NOT NULL,"
						+ "race TINYINT NOT NULL, personality TINYINT NOT NULL, kingdom INT, island INT,"
						+ " FOREIGN KEY (personId) REFERENCES people(id))");
		return V0_0_1;
	}
}
