package nl.focalor.utobot.base.input;

public class Input implements IInput {
	private final String input;

	public Input(String input) {
		super();
		this.input = input;
	}

	public String getInput() {
		return input;
	}

}
