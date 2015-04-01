package nl.focalor.utobot.utopia.service;

import java.util.List;

import nl.focalor.utobot.utopia.model.Attack;

public interface IAttackService {
	public List<Attack> find();

	public void create(Attack attack, boolean persist);

	public void delete(long id);
}
