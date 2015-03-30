package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.utopia.dao.ISpellDao;
import nl.focalor.utobot.utopia.model.SpellCast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellService implements ISpellService {
	@Autowired
	private ISpellDao spellDao;

	@Override
	public void createCast(SpellCast spell) {
		spellDao.createCast(spell);
	}

	@Override
	public void deleteCast(long id) {
		spellDao.deleteCast(id);
	}

	@Override
	public void delete(long id) {
		spellDao.delete(id);
	}

}
