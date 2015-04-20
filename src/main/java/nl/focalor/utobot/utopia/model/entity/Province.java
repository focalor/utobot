package nl.focalor.utobot.utopia.model.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.utopia.model.Personality;
import nl.focalor.utobot.utopia.model.Race;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "island", "kingdom"}))
public class Province {
	private static Pattern PROVINCE_PATTERN = Pattern.compile("(.*) \\((\\d{1,2}):(\\d{1,2})\\)");

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String name;
	private Integer island;
	private Integer kingdom;
	private Race race;
	private Personality personality;
	@OneToOne(mappedBy = "province")
	private Person owner;

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

	public Integer getIsland() {
		return island;
	}

	public void setIsland(Integer island) {
		this.island = island;
	}

	public Integer getKingdom() {
		return kingdom;
	}

	public void setKingdom(Integer kingdom) {
		this.kingdom = kingdom;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public static Province createFor(String province) {
		Matcher matcher = PROVINCE_PATTERN.matcher(province);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Illegal input, expected PROVINCE (KD:ISLAND)");
		}

		Province prov = new Province();
		prov.setName(matcher.group(1));
		prov.setKingdom(Integer.valueOf(matcher.group(2)));
		prov.setIsland(Integer.valueOf(matcher.group(3)));
		return prov;
	}
}
