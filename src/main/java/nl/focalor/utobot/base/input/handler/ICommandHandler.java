package nl.focalor.utobot.base.input.handler;

import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;

public interface ICommandHandler extends IInputHandler {

	public List<String> getCommandNames();

	public IResult handleCommand(CommandInput event);
}
