package nl.focalor.utobot.base.model.service;

import java.util.List;

import nl.focalor.utobot.base.model.Person;

public interface IPersonService {
	public void create(Person person);

	public Person find(String name);

	public List<Person> loadPeople(String namePart, String provinceNamePart);
}
