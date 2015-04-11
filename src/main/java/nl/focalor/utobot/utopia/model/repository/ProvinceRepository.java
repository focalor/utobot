package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.Province;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface ProvinceRepository extends CrudRepository<Province, Long> {

    @Query("SELECT p FROM Province p JOIN p.owner o WHERE o.id = ?1 OR p.name LIKE %?2%")
    List<Province> findByPersonOrNamePart(Long personId, String namePart);
}
