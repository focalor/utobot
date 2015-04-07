package nl.focalor.utobot.base.model.dao;

import java.util.Set;
import nl.focalor.utobot.base.model.Person;

public interface IPersonDao {
	public void create(Person person);

	public void addNick(long personId, String nick);

	public Person get(long id);

	public Set<Person> find(String name, boolean fuzzy);
}
