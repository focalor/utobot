package nl.focalor.utobot.utopia.job;

import nl.focalor.utobot.base.jobs.IScheduledJob;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.model.entity.SpellCast;
import nl.focalor.utobot.utopia.service.ISpellService;

public class SpellCastCompletedJob implements IScheduledJob {
	private final IBotService botService;
	private final ISpellService spellService;
	private final IPersonService personService;
	private final SpellCast cast;

	public SpellCastCompletedJob(IBotService botService, ISpellService spellService, IPersonService personService,
			SpellCast cast) {
		super();
		this.botService = botService;
		this.spellService = spellService;
		this.personService = personService;
		this.cast = cast;
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		builder.append(cast.getCaster().getOwner().getName());
		builder.append("'s ");
		builder.append(cast.getId());
		builder.append(" has ended");

		botService.broadcast(builder.toString());
		spellService.delete(cast);
	}

}