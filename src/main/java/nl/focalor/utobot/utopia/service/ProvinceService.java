package nl.focalor.utobot.utopia.service;

import java.util.List;

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
	public List<Province> find(String namePart) {
		return provinceDao.findByNamePart(namePart);
	}

	@Override
	public void create(Province province) {
		provinceDao.save(province);
	}

}
