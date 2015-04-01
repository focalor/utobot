package nl.focalor.utobot.base.jobs;

import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellCastService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

public class StartupJob implements IScheduledJob {
	private final IUtopiaService utopiaService;
	private final IAttackService attackService;
	private final ISpellCastService spellCastService;

	public StartupJob(IUtopiaService utopiaService,
			IAttackService attackService, ISpellCastService spellCastService) {
		super();
		this.utopiaService = utopiaService;
		this.attackService = attackService;
		this.spellCastService = spellCastService;
	}

	@Override
	public void run() {
		System.out.println(utopiaService.getUtopiaDate().toString());

		// Load attacks
		attackService.find().stream()
				.forEach(attack -> attackService.create(attack, false));
		spellCastService
				.find()
				.stream()
				.forEach(spellCast -> spellCastService.create(spellCast, false));
	}

}
