package nl.focalor.utobot.base.input.handler;

import java.util.regex.Pattern;

public abstract class AbstractRegexHandler implements IGenericRegexHandler {
	private final Pattern pattern;
	private final String name;

	public AbstractRegexHandler(String name, String regex) {
		super();
		this.pattern = Pattern.compile(regex);
		this.name = name;
	}

	@Override
	public Pattern getRegexPattern() {
		return pattern;
	}

	@Override
	public String getName() {
		return name;
	}
}
