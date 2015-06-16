package nl.focalor.utobot.base.service;

import java.util.List;

public interface IBotService {
	public void startBot();

	public void broadcast(String message);

	public void broadcast(List<String> messages);

	public void setTopic(String topic);
}
