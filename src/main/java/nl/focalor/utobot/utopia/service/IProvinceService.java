package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.entity.Province;

public interface IProvinceService {

	public Province create(Province province);

	public void deleteByNameIgnoreCase(String name);

	public Province find(Long personId);

	public Province findByNameAndIslandAndKingdom(String name, int island, int kingdom);

}
