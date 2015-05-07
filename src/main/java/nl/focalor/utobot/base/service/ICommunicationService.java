package nl.focalor.utobot.base.service;

public interface ICommunicationService {
	public void setTopic(String topic);

	public void broadcastMessage(String message);
}
