package nl.focalor.utobot.base.model.repository;

import java.util.List;

import nl.focalor.utobot.base.model.entity.Person;

import org.springframework.data.jpa.repository.Query;

/**
 * Created by luigibanzato on 11/04/2015.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

	/**
	 * Finds all people with the specified (nick)name
	 *
	 * @param nameOrNick
	 *            the (nick)name to look for, must be in all lowercase
	 */
	@Query("SELECT DISTINCT(p) FROM Person p LEFT OUTER JOIN p.nicks n WHERE LOWER(p.name) LIKE %?1% OR LOWER(n.nick) LIKE %?1%")
	List<Person> findByNameOrNick(String nameOrNick);

	/**
	 * Finds all people with the specified (nick)name or province name
	 *
	 * @param searchString
	 *            the (nick)name or province name to look for, must be in all lowercase
	 */
	@Query("SELECT DISTINCT(p) FROM Person p " +
			"LEFT OUTER JOIN p.nicks n " +
			"LEFT OUTER JOIN p.province pr " +
			"WHERE LOWER(p.name) LIKE %?1% " +
			"OR LOWER(n.nick) LIKE %?1% " +
			"OR LOWER(pr.name) LIKE %?1%")
	List<Person> findByNickNameOrProvince(String searchString);
}
