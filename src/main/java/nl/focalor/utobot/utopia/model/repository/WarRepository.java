package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.War;

/**
 * Created by luigibanzato on 12/04/2015.
 */
public interface WarRepository extends CrudRepository<War, Long> {
	public War findByEndDateNull();
}
