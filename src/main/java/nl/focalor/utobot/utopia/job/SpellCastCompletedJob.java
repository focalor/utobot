package nl.focalor.utobot.utopia.job;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;

public class SpellCastCompletedJob implements IScheduledJob {
	private final IBotService botService;
	private final ISpellService spellService;
	private final SpellCast cast;

	public SpellCastCompletedJob(IBotService botService, ISpellService spellService, SpellCast cast) {
		super();
		this.botService = botService;
		this.spellService = spellService;
		this.cast = cast;
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		if (cast.getTarget() == null || cast.getTarget() == cast.getCaster().getProvince()) {
			builder.append(cast.getCaster().getName());
		} else {// TODO show caster if it was selfspell?
			builder.append(cast.getTarget().getName());
		}
		builder.append("'s ");
		builder.append(cast.getSpellId());
		builder.append(" has ended");

		botService.broadcast(builder.toString());
		spellService.delete(cast);
	}

}
