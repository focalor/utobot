package nl.focalor.utobot.base.model.service;

import java.util.List;
import java.util.Set;

import nl.focalor.utobot.base.model.entity.Person;

public interface IPersonService {
	public Person get(long id);

	public void save(Person person);

	public void addNick(long personId, String nick);

	public List<Person> find();

	public Person find(String name, Boolean fuzzy);

	public List<Person> load();

	public Set<Person> load(String name, String provinceName, Boolean fuzzy);

	public List<Person> findAll();
}
