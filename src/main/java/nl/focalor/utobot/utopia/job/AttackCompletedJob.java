package nl.focalor.utobot.utopia.job;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;

public class AttackCompletedJob implements IScheduledJob {
	private final IBotService botService;
	private final IAttackService attackService;
	private final Attack attack;

	public AttackCompletedJob(IBotService botService, IAttackService attackService, Attack attack) {
		super();
		this.botService = botService;
		this.attackService = attackService;
		this.attack = attack;
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		builder.append(attack.getPerson().getName());
		builder.append("'s army has returned");
		botService.broadcast(builder.toString());

		attackService.delete(attack);
	}

}
