package nl.focalor.utobot.irc.input;

/**
 * @author focalor
 */
public enum InputParameter {
	MESSAGE_EVENT("MessageEvent");

	private final String name;

	private InputParameter(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

}
