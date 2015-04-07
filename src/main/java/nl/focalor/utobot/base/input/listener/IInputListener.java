package nl.focalor.utobot.base.input.listener;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IInput;
import nl.focalor.utobot.base.input.IResult;

public interface IInputListener {

	public IResult onMessage(String source, String message);

	public IResult onNonCommand(IInput input);

	public IResult onCommand(CommandInput command);

}
