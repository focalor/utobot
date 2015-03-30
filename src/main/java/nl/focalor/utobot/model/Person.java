package nl.focalor.utobot.model;

import nl.focalor.utobot.utopia.model.Province;

public class Person {
	private Long id;
	private String name;
	private Province province;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
