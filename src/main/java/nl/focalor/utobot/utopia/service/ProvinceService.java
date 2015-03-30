package nl.focalor.utobot.utopia.service;

import java.util.List;

import nl.focalor.utobot.utopia.dao.IProvinceDao;
import nl.focalor.utobot.utopia.model.Province;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService implements IProvinceService {
	@Autowired
	private IProvinceDao provinceDao;

	@Override
	public List<Province> find(Long personId, String namePart) {
		return provinceDao.find(personId, namePart);
	}

	@Override
	public void create(Province province) {
		provinceDao.create(province);
	}

}
