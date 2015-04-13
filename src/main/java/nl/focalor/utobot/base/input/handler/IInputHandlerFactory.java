package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;

public interface IInputHandlerFactory extends IInputHandler {
	public default List<ICommandHandler> getCommandHandlers() {
		return new ArrayList<>();
	}

	public default List<IRegexHandler> getRegexHandlers() {
		return new ArrayList<>();
	}
}
