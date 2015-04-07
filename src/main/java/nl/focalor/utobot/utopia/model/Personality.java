package nl.focalor.utobot.utopia.model;

public enum Personality {
	UNKNOWN(0),
	MERCHANT(1),
	SAGE(2),
	ROGUE(3),
	MYSTIC(4),
	WARRIOR(5),
	TACTICIAN(6),
	CLERIC(7),
	WAR_HERO(8);

	private final int id;

	private Personality(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Personality valueOf(int id) {
		for (Personality personality : Personality.values()) {
			if (personality.id == id) {
				return personality;
			}
		}
		return UNKNOWN;
	}
}
