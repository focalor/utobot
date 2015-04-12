package nl.focalor.utobot.utopia.model.repository;

import java.util.List;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.Province;

import org.springframework.data.jpa.repository.Query;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface ProvinceRepository extends CrudRepository<Province, Long> {

	/**
	 * Finds all provinces with the specified namePart
	 *
	 * @param namePart
	 *            the namePart to look for, must be in all lowercase
	 */
	@Query("SELECT p FROM Province p WHERE LOWER(p.name) LIKE %?1%")
	List<Province> findByNamePart(String namePart);
}
