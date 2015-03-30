package nl.focalor.utobot.model.service;

import java.util.List;

import nl.focalor.utobot.model.Person;

public interface IPersonService {
	public void create(Person person);

	public List<Person> loadPeople(String namePart, String provinceNamePart);
}
