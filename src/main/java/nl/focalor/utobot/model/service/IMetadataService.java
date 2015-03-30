package nl.focalor.utobot.model.service;

import nl.focalor.utobot.util.Version;

public interface IMetadataService {
	public Version upgradeToCurrentVersion();

	public Version getSchemaVersion();

	public Version migrateFromVersion(Version version);
}
