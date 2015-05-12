package nl.focalor.utobot.base.input;

import java.util.HashMap;
import java.util.Map;
import nl.focalor.utobot.base.input.listener.IInputListener;

public class Input implements IInput {
	private final IInputListener dispatcher;
	private final String input;
	private final String user;
	private final String room;
	private final Map<String, Object> parameters = new HashMap<>();

	public Input(IInputListener dispatcher, String room, String user, String input) {
		super();
		this.dispatcher = dispatcher;
		this.user = user;
		this.room = room;
		this.input = input;
	}

	@Override
	public String getInput() {
		return input;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getRoom() {
		return room;
	}

	@Override
	public IInputListener getDispatcher() {
		return dispatcher;
	}

	@Override
	public void putParameter(String name, Object param) {
		this.parameters.put(name, param);
	}

	@Override
	public void putParameters(Map<String, Object> parameters) {
		this.parameters.putAll(parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String name) {
		return (T) this.parameters.get(name);
	}
}
