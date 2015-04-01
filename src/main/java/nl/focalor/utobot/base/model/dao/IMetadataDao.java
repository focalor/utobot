package nl.focalor.utobot.base.model.dao;

import nl.focalor.utobot.util.Version;

public interface IMetadataDao {
	public Version getSchemaVersion();
}
