package nl.focalor.utobot.base.input.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;

public interface IRegexHandler {
	public IResult handleInput(IInput input);

	public Pattern getRegexPattern();

	public default Matcher getMatcher(String input) {
		return getRegexPattern().matcher(input);
	}

	public default boolean matches(IInput input) {
		return getMatcher(input.getInput()).matches();
	}
}
