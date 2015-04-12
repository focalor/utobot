package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.entity.SpellCast;

import java.util.List;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface SpellCastRepository extends CrudRepository<SpellCast, Long> {
    List<SpellCast> findByCaster(Province caster);
}
