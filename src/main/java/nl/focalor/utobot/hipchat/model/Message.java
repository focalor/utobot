package nl.focalor.utobot.hipchat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private String message;
	private User from;
	private Boolean notify;

	@JsonProperty("message_format")
	private String messageFormat;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

}
