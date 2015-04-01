package nl.focalor.utobot.utopia.job;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellCastService;

public class SpellCastCompletedJob implements IScheduledJob {
	private final IBotService botService;
	private final ISpellCastService spellCastService;
	private final SpellCast cast;

	public SpellCastCompletedJob(IBotService botService,
			ISpellCastService spellCastService, SpellCast cast) {
		super();
		this.botService = botService;
		this.spellCastService = spellCastService;
		this.cast = cast;
	}

	@Override
	public void run() {
		String msg = cast.getSpellId() + " ended";
		botService.broadcast(msg);
		spellCastService.delete(cast.getId());
	}

}