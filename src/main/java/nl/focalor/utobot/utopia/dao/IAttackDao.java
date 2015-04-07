package nl.focalor.utobot.utopia.dao;

import java.util.List;
import nl.focalor.utobot.utopia.model.Attack;

public interface IAttackDao {
	public Attack get(long id);

	public List<Attack> find(Long personId, String person);

	public void create(Attack attack);

	public void delete(long id);
}
