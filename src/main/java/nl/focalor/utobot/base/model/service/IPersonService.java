package nl.focalor.utobot.base.model.service;

import java.util.List;
import nl.focalor.utobot.base.model.entity.Person;

public interface IPersonService {
	public Person get(long id);

	public void save(Person person);

	public void addNick(long personId, String nick);

	public Person find(String name, Boolean fuzzy);

	public List<Person> load();

	public List<Person> findByNickNameOrProvince(String searchString);

	public List<Person> findAll();
}
