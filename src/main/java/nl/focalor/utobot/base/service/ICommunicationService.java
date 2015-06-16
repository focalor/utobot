package nl.focalor.utobot.base.service;

import java.util.List;

public interface ICommunicationService {
	public void setTopic(String topic);

	public void broadcast(String message);

	public default void broadcast(List<String> messages) {
		for (String message : messages) {
			broadcast(message);
		}
	}
}
