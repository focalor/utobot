package nl.focalor.utobot.base.input;

import java.util.Map;
import nl.focalor.utobot.base.input.listener.IInputListener;

public interface IInput {
	public String getUser();

	public String getRoom();

	public String getInput();

	public IInputListener getDispatcher();

	public void putParameter(String name, Object param);

	public void putParameters(Map<String, Object> parameters);

	public <T> T getParameter(String name);
}
