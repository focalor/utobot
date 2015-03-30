package nl.focalor.utobot.base.input;

public class ReplyResult implements IResult {
	private final String message;

	public ReplyResult(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
