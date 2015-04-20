package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService implements IProvinceService {

	@Autowired
	private ProvinceRepository provinceDao;

	@Override
	public Province find(Long personId) {
		return provinceDao.findOne(personId);
	}

	@Override
	public Province create(Province province) {
		return provinceDao.save(province);
	}

	@Override
	public void deleteByNameIgnoreCase(String name) {
		provinceDao.deleteByNameIgnoreCase(name);
	}

	@Override
	public Province findByNameAndIslandAndKingdom(String name, int island, int kingdom) {
		return provinceDao.findByNameAndIslandAndKingdom(name, island, kingdom);
	}
}
