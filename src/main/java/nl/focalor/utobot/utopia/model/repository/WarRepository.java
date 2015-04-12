package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.War;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by luigibanzato on 12/04/2015.
 */
public interface WarRepository extends CrudRepository<War, Long> {
    @Query("SELECT w FROM War w WHERE w.endDate IS NULL")
    public War findOpenWar();
}
