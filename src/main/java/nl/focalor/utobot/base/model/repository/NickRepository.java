package nl.focalor.utobot.base.model.repository;

import nl.focalor.utobot.base.model.entity.Nick;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface NickRepository extends CrudRepository<Nick, Long> {
	void deleteByNickIgnoreCase(String nick);
}
