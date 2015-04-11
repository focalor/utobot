package nl.focalor.utobot.utopia.model.repository;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.repository.CrudRepository;
import nl.focalor.utobot.utopia.model.entity.Attack;

import java.util.List;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface AttackRepository extends CrudRepository<Attack, Long> {
    List<Attack> findByPerson(Person person);
}
