package nl.focalor.utobot.base.input.handler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;

public interface IRegexHandler extends IInputHandler {

	public IResult handleInput(IInput input);

	public Pattern getRegexPattern();

	public default Matcher getMatcher(IInput input) {
		return getMatcher(input.getInput());
	}

	public default Matcher getMatcher(String input) {
		return getRegexPattern().matcher(input);
	}

	public default boolean find(IInput input) {
		return getMatcher(input.getInput()).find();
	}

	@Override
	public default List<String> getHelpBody() {
		return Arrays.asList("Following syntax is supported:", getRegexPattern().pattern());
	}

}
