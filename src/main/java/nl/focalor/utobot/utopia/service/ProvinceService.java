package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService implements IProvinceService {

	@Autowired
	private ProvinceRepository provinceDao;

	@Override
	public List<Province> find(Long personId, String namePart) {
		return provinceDao.findByPersonOrNamePart(personId, namePart);
	}

	@Override
	public void create(Province province) {
		provinceDao.save(province);
	}

}
