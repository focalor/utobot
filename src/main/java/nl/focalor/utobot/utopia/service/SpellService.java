package nl.focalor.utobot.utopia.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.dao.ISpellDao;
import nl.focalor.utobot.utopia.job.SpellCastCompletedJob;
import nl.focalor.utobot.utopia.model.Spell;
import nl.focalor.utobot.utopia.model.SpellCast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpellService implements ISpellCastService {
	@Autowired
	private ISpellDao spellCastDao;
	@Autowired
	private IJobsService jobsService;
	@Autowired
	private IBotService botService;

	private final Map<String, Spell> knownSpells;

	@Autowired
	public SpellService(ObjectMapper mapper,
			@Value("${spells.file}") String spellsFile)
			throws JsonParseException, JsonMappingException, IOException {
		super();

		JavaType type = mapper.getTypeFactory().constructCollectionType(
				List.class, Spell.class);

		List<Spell> spells = mapper.readValue(this.getClass().getClassLoader()
				.getResource(spellsFile), type);

		knownSpells = new HashMap<>();
		for (Spell spell : spells) {
			knownSpells.put(spell.getId(), spell);
		}
	}

	@Override
	public String getNameForSpell(String id) {
		Spell spell = knownSpells.get(id);
		return spell == null ? null : spell.getName();
	}

	@Override
	public Collection<Spell> getKnownSpells() {
		return knownSpells.values();
	}

	@Override
	public void create(SpellCast cast, boolean persist) {
		if (persist) {
			spellCastDao.create(cast);
		}
		jobsService.scheduleAction(new SpellCastCompletedJob(botService, this,
				cast), cast.getLastHour());
	}

	@Override
	public void delete(long id) {
		spellCastDao.delete(id);
	}

	@Override
	public List<SpellCast> find() {
		return spellCastDao.find();
	}
}
