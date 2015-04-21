package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.List;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.entity.SpellCast;

public interface ISpellService {
	public Collection<Spell> getKnownSpells();

	public void create(SpellCast cast, boolean persist);

	public List<SpellCast> findByCaster(Person prov);

	public void delete(SpellCast spellCast);

	public List<SpellCast> findAll();
}
