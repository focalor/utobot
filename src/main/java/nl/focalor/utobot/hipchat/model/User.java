package nl.focalor.utobot.hipchat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private Integer id;
	private String name;
	@JsonProperty("mention_name")
	private String mentionName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMentionName() {
		return mentionName;
	}

	public void setMentionName(String mentionName) {
		this.mentionName = mentionName;
	}

}
