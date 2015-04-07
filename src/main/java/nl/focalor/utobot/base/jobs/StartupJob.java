package nl.focalor.utobot.base.jobs;

import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

public class StartupJob implements IScheduledJob {
	private final IUtopiaService utopiaService;
	private final IAttackService attackService;
	private final ISpellService spellService;

	public StartupJob(IUtopiaService utopiaService, IAttackService attackService, ISpellService spellService) {
		super();
		this.utopiaService = utopiaService;
		this.attackService = attackService;
		this.spellService = spellService;
	}

	@Override
	public void run() {
		System.out.println(utopiaService.getUtopiaDate().toString());

		// Load attacks
		attackService.find(null, null).stream().forEach(attack -> attackService.create(attack, false));
		spellService.find(null, null).stream().forEach(spellCast -> spellService.create(spellCast, false));
	}

}
