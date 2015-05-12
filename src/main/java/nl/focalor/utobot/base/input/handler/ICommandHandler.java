package nl.focalor.utobot.base.input.handler;

import java.util.Arrays;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;

/**
 * @author focalor
 */
public interface ICommandHandler extends IInputHandler {

	public default List<String> getCommandNames() {
		return Arrays.asList(getName());
	}

	public IResult handleCommand(CommandInput input);
}
