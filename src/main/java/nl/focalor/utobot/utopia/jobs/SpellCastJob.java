package nl.focalor.utobot.utopia.jobs;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;

public class SpellCastJob implements IScheduledJob {
	private final IBotService messagingService;
	private final ISpellService spellService;
	private final SpellCast spellCast;

	public SpellCastJob(IBotService messagingService,
			ISpellService spellService, SpellCast spellCast) {
		super();
		this.messagingService = messagingService;
		this.spellService = spellService;
		this.spellCast = spellCast;
	}

	@Override
	public void run() {
		messagingService.broadcast(spellCast.getSpell().getName() + " ended");
		spellService.deleteCast(spellCast.getId());
	}

}
