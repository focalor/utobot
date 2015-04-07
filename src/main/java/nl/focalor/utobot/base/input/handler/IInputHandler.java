package nl.focalor.utobot.base.input.handler;

public interface IInputHandler {
	public default String getHelp() {
		return null;
	}
}
