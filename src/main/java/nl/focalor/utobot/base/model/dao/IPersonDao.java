package nl.focalor.utobot.base.model.dao;

import java.util.List;

import nl.focalor.utobot.base.model.Person;

public interface IPersonDao {
	public void create(Person person);

	public Person get(long id);

	public List<Person> find(String namePart);
}
