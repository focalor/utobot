package nl.focalor.utobot.base.jobs;

import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

public class StartupJob implements IScheduledJob {
	private final IBotService botService;
	private final IUtopiaService utopiaService;
	private final IAttackService attackService;
	private final ISpellService spellService;

	public StartupJob(IBotService botService, IUtopiaService utopiaService, IAttackService attackService,
			ISpellService spellService) {
		super();
		this.botService = botService;
		this.utopiaService = utopiaService;
		this.attackService = attackService;
		this.spellService = spellService;
	}

	@Override
	public void run() {
		botService.startBot();
		botService.broadcast(utopiaService.getUtopiaDate().toString());

		// Load attacks and spells
		attackService.find(null, null).stream().forEach(attack -> attackService.create(attack, false));
		spellService.find(null, null).stream().forEach(spellCast -> spellService.create(spellCast, false));
	}

}
