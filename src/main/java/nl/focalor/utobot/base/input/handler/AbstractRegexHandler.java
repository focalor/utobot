package nl.focalor.utobot.base.input.handler;

import java.util.regex.Pattern;

public abstract class AbstractRegexHandler implements IRegexHandler {
	private final Pattern pattern;

	public AbstractRegexHandler(String regex) {
		super();
		pattern = Pattern.compile(regex);
	}

	@Override
	public Pattern getRegexPattern() {
		return pattern;
	}

}
