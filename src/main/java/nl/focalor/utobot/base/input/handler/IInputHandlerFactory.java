package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author focalor
 */
public interface IInputHandlerFactory extends IInputHandler {
	public default List<IGenericCommandHandler> getCommandHandlers() {
		return new ArrayList<>();
	}

	public default List<IGenericRegexHandler> getRegexHandlers() {
		return new ArrayList<>();
	}
}
