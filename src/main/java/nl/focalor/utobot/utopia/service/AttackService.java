package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.job.AttackCompletedJob;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.model.repository.AttackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class AttackService implements IAttackService {
	@Autowired
	private AttackRepository attackDao;
	@Autowired
	private IJobsService jobsService;
	@Autowired
	private IBotService botService;
	@Autowired
	private IPersonService personService;

	private final Collection<AttackType> knownAttacks;

	@Autowired
	public AttackService(UtopiaSettings utopiaSettings) {
		knownAttacks = utopiaSettings.getAttacks();
	}

	@Override
	@Transactional
	public void create(Attack attack, boolean persist) {
		if (persist) {
			attackDao.save(attack);
		}
		jobsService.scheduleAction(new AttackCompletedJob(botService, this, personService, attack),
				attack.getReturnDate());
	}

	@Override
	public List<Attack> findByPerson(Person person) {
		return attackDao.findByPerson(person);
	}

	@Override
	public void delete(Attack attack) {
		attackDao.delete(attack);
	}

	@Override
	public List<Attack> findAll() {
		return (List<Attack>) attackDao.findAll();
	}

	@Override
	public Collection<AttackType> getKnownAttackTypes() {
		return knownAttacks;
	}



}
