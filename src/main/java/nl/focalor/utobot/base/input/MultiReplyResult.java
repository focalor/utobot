package nl.focalor.utobot.base.input;

import java.util.List;

public class MultiReplyResult implements IResult {
	private final List<String> messages;

	public MultiReplyResult(List<String> messages) {
		super();
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
}
