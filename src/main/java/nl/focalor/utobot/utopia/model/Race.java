package nl.focalor.utobot.utopia.model;

public enum Race {
	UNKNOWN(0), AVIAN(1), DWARF(2), ELF(3), FAERY(4), HALFLING(5), HUMAN(6), ORC(
			7), UNDEAD(8);

	private final int id;

	private Race(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Race valueOf(int id) {
		for (Race race : Race.values()) {
			if (race.id == id) {
				return race;
			}
		}
		return UNKNOWN;
	}
}
