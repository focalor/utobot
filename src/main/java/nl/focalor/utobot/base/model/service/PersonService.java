package nl.focalor.utobot.base.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.focalor.utobot.base.model.Person;
import nl.focalor.utobot.base.model.dao.IPersonDao;
import nl.focalor.utobot.utopia.model.Province;
import nl.focalor.utobot.utopia.service.IProvinceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService implements IPersonService {
	@Autowired
	private IPersonDao personDao;
	@Autowired
	private IProvinceService provinceService;

	@Override
	@Transactional
	public void create(Person person) {
		personDao.create(person);
	}

	@Override
	@Transactional(readOnly = true)
	public Person get(long id) {
		return personDao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Person find(String name, Boolean fuzzy) {
		Set<Person> people = findNameOrNick(name, fuzzy);

		if (people.isEmpty()) {
			return null;
		} else if (people.size() == 1) {
			return people.iterator().next();
		} else {
			throw new RuntimeException("Multiple people found for name " + name);
		}
	}

	private Set<Person> findNameOrNick(String name, boolean fuzzy) {
		// Check for common postfixes
		int index = name.indexOf('|');
		final String strippedName;
		if (index > 0) {
			strippedName = name.substring(0, index);
		} else {
			strippedName = name;
		}

		return personDao.find(strippedName, fuzzy);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Person> load(String name, String province, Boolean fuzzy) {
		Set<Person> people = new HashSet<Person>();
		if (!StringUtils.isEmpty(name)) {
			people.addAll(loadPeopleByName(name, fuzzy));
		}
		if (!StringUtils.isEmpty(province)) {
			people.addAll(loadPeopleByProvinceName(province, fuzzy));
		}
		return people;
	}

	private Set<Person> loadPeopleByName(String namePart, boolean fuzzy) {
		Set<Person> result = findNameOrNick(namePart, fuzzy);
		for (Person person : result) {
			loadPersonInfo(person);
		}
		return result;
	}

	private void loadPersonInfo(Person person) {
		List<Province> provinces = provinceService.find(person.getId(), null, null);
		if (provinces.size() == 1) {
			person.setProvince(provinces.get(0));
		}
	}

	private List<Person> loadPeopleByProvinceName(String provinceNamePart, boolean fuzzy) {
		List<Person> result = new ArrayList<Person>();
		List<Province> provinces = provinceService.find(null, provinceNamePart, fuzzy);

		for (Province prov : provinces) {
			Person person = personDao.get(prov.getPersonId());
			person.setProvince(prov);
			result.add(person);
		}
		return result;
	}

	@Override
	@Transactional
	public void addNick(long personId, String nick) {
		personDao.addNick(personId, nick);
	}
}
