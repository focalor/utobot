package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.model.entity.Attack;

import java.util.Collection;
import java.util.List;

public interface IAttackService {
	public Collection<AttackType> getKnownAttackTypes();

	public List<Attack> findByPerson(Person person);

	public void create(Attack attack, boolean persist);

	public void delete(Attack attack);

	public List<Attack> findAll();
}
