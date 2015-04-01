package nl.focalor.utobot.base.input;


public interface IInputListener {

	public IResult onMessage(String source, String message);

	public IResult onNonCommand(IInput input);

	public IResult onCommand(CommandInput command);

}
