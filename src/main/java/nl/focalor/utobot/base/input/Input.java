package nl.focalor.utobot.base.input;

public class Input implements IInput {
	private final String input;
	private final String source;

	public Input(String source, String input) {
		super();
		this.source = source;
		this.input = input;
	}

	@Override
	public String getInput() {
		return input;
	}

	@Override
	public String getSource() {
		return source;
	}

}
