package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.Province;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface ProvinceRepository extends CrudRepository<Province, Long> {
	Province findByNameAndIslandAndKingdom(String name, int island, int kingdom);

	void deleteByNameIgnoreCase(String person);
}
