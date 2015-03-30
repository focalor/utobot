package nl.focalor.utobot.utopia.service;

import nl.focalor.utobot.base.jobs.IJobsService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.dao.IAttackDao;
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
	public void add(Attack attack) {
		// TODO backup to db
		jobsService.scheduleAction(() -> botService.broadcast("Army returned"),
				attack.getReturnDate());
	}
}
