package nl.focalor.utobot.model.service;

import nl.focalor.utobot.model.dao.IDatabaseMigrator;
import nl.focalor.utobot.model.dao.IMetadataDao;
import nl.focalor.utobot.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetadataService implements IMetadataService {
	@Autowired
	private IMetadataDao dao;
	@Autowired
	private IDatabaseMigrator databaseMigrator;

	@Override
	@Transactional(readOnly = true)
	public Version getSchemaVersion() {
		return dao.getSchemaVersion();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Version migrateFromVersion(Version version) {
		return databaseMigrator.migrateFromVersion(version);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Version upgradeToCurrentVersion() {
		return migrateFromVersion(getSchemaVersion());
	}

}
