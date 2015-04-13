package nl.focalor.utobot.base.model.service;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.model.entity.Nick;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.repository.NickRepository;
import nl.focalor.utobot.base.model.repository.PersonRepository;
import nl.focalor.utobot.utopia.service.IProvinceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements IPersonService {
	@Autowired
	private PersonRepository personDao;

	@Autowired
	private NickRepository nickDao;

	@Autowired
	private IProvinceService provinceService;

	@Override
	@Transactional
	public void save(Person person) {
		personDao.save(person);
	}

	@Override
	@Transactional(readOnly = true)
	public Person get(long id) {
		return personDao.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Person find(String name, Boolean fuzzy) {
		List<Person> people = findNameOrNick(name, fuzzy);

		if (people.isEmpty()) {
			return null;
		} else if (people.size() == 1) {
			return people.iterator().next();
		} else {
			throw new RuntimeException("Multiple people found for name " + name);
		}
	}

	private List<Person> findNameOrNick(String name, boolean fuzzy) {
		// Check for common postfixes
		int index = name.indexOf('|');
		final String strippedName;
		if (index > 0) {
			strippedName = name.substring(0, index);
		} else {
			strippedName = name;
		}

		return personDao.findByNameOrNick(strippedName.toLowerCase());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> load() {
		List<Person> people = findAll();
		people.stream().forEach(person -> loadPersonInfo(person));
		return people;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Person> findByNickNameOrProvince(String searchString) {
		List<Person> people = new ArrayList<Person>();

		if (!StringUtils.isEmpty(searchString)) {
			people = personDao.findByNickNameOrProvince(searchString.toLowerCase());
		}

		return people;
	}

	@Override
	public List<Person> findAll() {
		return (List<Person>) personDao.findAll();
	}

	private void loadPersonInfo(Person person) {
		person.setProvince(provinceService.find(person.getId()));
	}

	@Override
	@Transactional
	public void addNick(long personId, String nick) {
		Person person = personDao.findOne(personId);
		Nick newNick = new Nick();
		newNick.setNick(nick);
		newNick.setPerson(person);
		nickDao.save(newNick);
	}
}
