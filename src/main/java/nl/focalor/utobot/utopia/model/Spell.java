package nl.focalor.utobot.utopia.model;

public class Spell {
	private String id;
	private String name;
	private String selfSyntax;
	private String targetSyntax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelfSyntax() {
		return selfSyntax;
	}

	public void setSelfSyntax(String selfSyntax) {
		this.selfSyntax = selfSyntax;
	}

	public String getTargetSyntax() {
		return targetSyntax;
	}

	public void setTargetSyntax(String targetSyntax) {
		this.targetSyntax = targetSyntax;
	}

}
