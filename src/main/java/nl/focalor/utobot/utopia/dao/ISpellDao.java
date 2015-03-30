package nl.focalor.utobot.utopia.dao;

import nl.focalor.utobot.utopia.model.SpellCast;

public interface ISpellDao {
	public void createCast(SpellCast spell);

	public void delete(long id);

	public void deleteCast(long id);
}
