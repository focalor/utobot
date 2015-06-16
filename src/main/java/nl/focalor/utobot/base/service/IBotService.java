package nl.focalor.utobot.base.service;

public interface IBotService {
	public void startBot();

	public void broadcast(String message);

	public void setTopic(String topic);
}
