package nl.focalor.utobot.utopia.job;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

public class AttackCompletedJob implements IScheduledJob {
	private final IBotService botService;
	private final IAttackService attackService;
	private final IPersonService personService;
	private final Attack attack;

	public AttackCompletedJob(IBotService botService, IAttackService attackService, IPersonService personService,
			Attack attack) {
		super();
		this.botService = botService;
		this.attackService = attackService;
		this.personService = personService;
		this.attack = attack;
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		if (attack.getPersonId() == null) {
			builder.append(attack.getPerson());
		} else {
			builder.append(personService.get(attack.getPersonId()).getName());
		}
		builder.append("'s army has returned");
		botService.broadcast(builder.toString());

		attackService.delete(attack.getId());
	}

}
