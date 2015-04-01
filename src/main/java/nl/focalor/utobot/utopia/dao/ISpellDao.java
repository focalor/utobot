package nl.focalor.utobot.utopia.dao;

import java.util.List;

import nl.focalor.utobot.utopia.model.SpellCast;

public interface ISpellDao {
	public void create(SpellCast spell);

	public void delete(long id);

	public List<SpellCast> find();

}
