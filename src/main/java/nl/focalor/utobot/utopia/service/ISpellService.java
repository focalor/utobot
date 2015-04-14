package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.entity.Province;
import nl.focalor.utobot.utopia.model.entity.SpellCast;

import java.util.Collection;
import java.util.List;

public interface ISpellService {
	public Collection<Spell> getKnownSpells();

	public void create(SpellCast cast, boolean persist);

	public List<SpellCast> findByCaster(Province prov);

	public void delete(SpellCast spellCast);

	public List<SpellCast> findAll();
}
