package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.List;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.model.AttackType;

public interface IAttackService {
	public Collection<AttackType> getKnownAttackTypes();

	public List<Attack> find(Long personId, String person);

	public void create(Attack attack, boolean persist);

	public void delete(long id);
}
