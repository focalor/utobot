package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.dao.ISpellCastDao;
import nl.focalor.utobot.utopia.job.SpellCastCompletedJob;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.model.SpellType;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpellService implements ISpellService {
	@Autowired
	private ISpellCastDao spellCastDao;
	@Autowired
	private IJobsService jobsService;
	@Autowired
	private IBotService botService;

	private final Map<String, SpellType> knownSpells;

	@Autowired
	public SpellService(UtopiaSettings settings) {
		super();

		knownSpells = new HashMap<>();
		for (SpellType spell : settings.getSpells()) {
			knownSpells.put(spell.getId(), spell);
		}
	}

	@Override
	public Collection<SpellType> getKnownSpellTypes() {
		return knownSpells.values();
	}

	@Override
	@Transactional
	public void create(SpellCast cast, boolean persist) {
		if (persist) {
			spellCastDao.create(cast);
		}
		jobsService.scheduleAction(new SpellCastCompletedJob(botService, this, cast), cast.getLastHour());
	}

	@Override
	public void delete(long id) {
		spellCastDao.delete(id);
	}

	@Override
	public List<SpellCast> find(Long personId, String person) {
		return spellCastDao.find(personId, person);
	}
}
