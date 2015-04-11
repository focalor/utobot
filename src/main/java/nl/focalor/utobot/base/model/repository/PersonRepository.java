package nl.focalor.utobot.base.model.repository;

import nl.focalor.utobot.base.model.entity.Nick;
import nl.focalor.utobot.base.model.entity.Person;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p LEFT OUTER JOIN p.nicks n WHERE p.name LIKE %?1% OR n.nick LIKE %?1%")
    List<Person> findByNameOrNick(String nameOrNick);
}
