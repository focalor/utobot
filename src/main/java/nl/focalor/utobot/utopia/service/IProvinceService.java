package nl.focalor.utobot.utopia.service;

import java.util.List;

import nl.focalor.utobot.utopia.model.entity.Province;

public interface IProvinceService {

	public void create(Province province);

	public Province find(Long personId);

	public List<Province> find(String namePart);
}
