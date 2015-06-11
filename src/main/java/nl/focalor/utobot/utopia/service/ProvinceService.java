package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.repository.ProvinceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinceService implements IProvinceService {
	@Autowired
	private IPersonService personService;
	@Autowired
	private ProvinceRepository provinceDao;

	@Override
	@Transactional(readOnly = true)
	public Province find(Long personId) {
		return provinceDao.findOne(personId);
	}

	@Override
	@Transactional
	public Province create(Province province) {
		return provinceDao.save(province);
	}

	@Override
	@Transactional
	public void deleteByNameIgnoreCase(String name) {
		Province prov = provinceDao.findByNameIgnoreCase(name);
		if (prov != null) {
			Person owner = prov.getOwner();
			owner.setProvince(null);
			personService.save(owner);

			provinceDao.deleteByNameIgnoreCase(name);
		}
	}

	@Override
	public Province findByNameAndIslandAndKingdom(String name, int island, int kingdom) {
		return provinceDao.findByNameAndIslandAndKingdom(name, island, kingdom);
	}
}
