package nl.focalor.utobot.base.model.service;

import nl.focalor.utobot.base.model.dao.IMetadataDao;
import nl.focalor.utobot.util.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetadataService implements IMetadataService {
	@Autowired
	private IMetadataDao dao;

	@Override
	@Transactional(readOnly = true)
	public Version getSchemaVersion() {
		return dao.getSchemaVersion();
	}

}
