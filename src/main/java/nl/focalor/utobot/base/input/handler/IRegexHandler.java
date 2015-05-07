package nl.focalor.utobot.base.input.handler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;

/**
 * @author focalor
 */
public interface IRegexHandler extends IInputHandler {

	public Pattern getRegexPattern();

	/**
	 * Never returns null
	 */
	public IResult handleInput(Matcher matcher, IInput input);

	public default IResult handleInput(IInput input) {
		Matcher matcher = getMatcher(input);
		if (!matcher.find()) {
			return new ErrorResult("Input does not match expected input");
		}
		return handleInput(matcher, input);
	}

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
