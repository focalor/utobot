package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.Province;

import java.util.List;

public interface IProvinceService {

	public void create(Province province);

	public List<Province> find(Long personId, String namePart);
}
