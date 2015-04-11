package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.SpellType;
import nl.focalor.utobot.utopia.model.entity.SpellCast;

import java.util.Collection;
import java.util.List;

public interface ISpellService {
	public Collection<SpellType> getKnownSpellTypes();

	public void create(SpellCast cast, boolean persist);

	public List<SpellCast> findByPerson(Person person);

	public void delete(SpellCast spellCast);

	public List<SpellCast> findAll();
}
