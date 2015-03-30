package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.SpellCast;

public interface ISpellService {
	public void createCast(SpellCast spell);

	public void delete(long id);

	public void deleteCast(long id);
}
