package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.List;

import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.SpellCast;

public interface ISpellCastService {
	public String getNameForSpell(String id);

	public Collection<Spell> getKnownSpells();

	public void create(SpellCast cast, boolean persist);

	public List<SpellCast> find();

	public void delete(long id);
}
