package nl.focalor.utobot.utopia.service;

import java.util.List;

import nl.focalor.utobot.utopia.model.Province;

public interface IProvinceService {

	public void create(Province province);

	public List<Province> find(Long personId, String namePart);
}
