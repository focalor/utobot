package nl.focalor.utobot.base.model;

import java.util.List;

/**
 * @author focalor
 */
public class Link {
	private List<String> ids;
	private String link;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
