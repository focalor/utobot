package nl.focalor.utobot.hipchat.model;

import java.util.List;

/**
 * @author focalor
 */
public class Webhooks {
	private List<Webhook> items;

	public List<Webhook> getItems() {
		return items;
	}

	public void setItems(List<Webhook> items) {
		this.items = items;
	}

}
