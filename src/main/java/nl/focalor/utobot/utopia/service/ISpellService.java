package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.List;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.model.SpellType;

public interface ISpellService {
	public Collection<SpellType> getKnownSpellTypes();

	public void create(SpellCast cast, boolean persist);

	public List<SpellCast> find(Long personId, String person);

	public void delete(long id);
}
