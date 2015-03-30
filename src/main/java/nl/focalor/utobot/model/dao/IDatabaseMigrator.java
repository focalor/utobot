package nl.focalor.utobot.model.dao;

import nl.focalor.utobot.util.Version;

public interface IDatabaseMigrator {

	public Version migrateFromVersion(Version version);
}
