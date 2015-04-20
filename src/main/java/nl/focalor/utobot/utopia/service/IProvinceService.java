package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.Province;

public interface IProvinceService {

	public void create(Province province);

	public void deleteByNameIgnoreCase(String name);

	public Province find(Long personId);

}
