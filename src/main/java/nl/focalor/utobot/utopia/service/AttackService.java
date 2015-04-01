package nl.focalor.utobot.utopia.service;

import java.util.List;

import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.dao.IAttackDao;
import nl.focalor.utobot.utopia.job.AttackCompletedJob;
import nl.focalor.utobot.utopia.model.Attack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttackService implements IAttackService {
	@Autowired
	private IAttackDao attackDao;
	@Autowired
	private IJobsService jobsService;
	@Autowired
	private IBotService botService;

	@Override
	public void create(Attack attack, boolean persist) {
		if (persist) {
			attackDao.create(attack);
		}
		jobsService.scheduleAction(new AttackCompletedJob(botService, this,
				attack), attack.getReturnDate());
	}

	@Override
	public List<Attack> find() {
		return attackDao.find();
	}

	@Override
	public void delete(long id) {
		attackDao.delete(id);
	}

}
