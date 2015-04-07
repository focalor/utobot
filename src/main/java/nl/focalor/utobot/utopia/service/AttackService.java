package nl.focalor.utobot.utopia.service;

import java.util.Collection;
import java.util.List;
import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.dao.IAttackDao;
import nl.focalor.utobot.utopia.job.AttackCompletedJob;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttackService implements IAttackService {
	@Autowired
	private IAttackDao attackDao;
	@Autowired
	private IJobsService jobsService;
	@Autowired
	private IBotService botService;

	private final Collection<AttackType> knownAttacks;

	@Autowired
	public AttackService(UtopiaSettings utopiaSettings) {
		knownAttacks = utopiaSettings.getAttacks();
	}

	@Override
	@Transactional
	public void create(Attack attack, boolean persist) {
		if (persist) {
			attackDao.create(attack);
		}
		jobsService.scheduleAction(new AttackCompletedJob(botService, this, attack), attack.getReturnDate());
	}

	@Override
	public List<Attack> find(Long personId, String person) {
		return attackDao.find(personId, person);
	}

	@Override
	public void delete(long id) {
		attackDao.delete(id);
	}

	@Override
	public Collection<AttackType> getKnownAttackTypes() {
		return knownAttacks;
	}

}
