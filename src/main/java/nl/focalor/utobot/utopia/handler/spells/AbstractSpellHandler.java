package nl.focalor.utobot.utopia.handler.spells;

import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.AbstractRegexHandler;

/**
 * @author focalor
 */
public abstract class AbstractSpellHandler extends AbstractRegexHandler {

	public AbstractSpellHandler(String name, String regex) {
		super(name, regex);
	}

	public ReplyResult buildResponse(String spellName, String target, int duration) {
		StringBuilder builder = new StringBuilder();
		builder.append(spellName);
		builder.append(" added for ");
		builder.append(target);
		builder.append(" for ");
		builder.append(duration);
		builder.append(" hours");
		return new ReplyResult(builder.toString());
	}

	@Override
	public boolean hasHelp() {
		return false;
	}
}