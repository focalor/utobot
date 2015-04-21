package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;

public interface IInputHandler {
	public String getName();

	public default boolean hasHelp() {
		return true;
	}

	public default List<String> getHelp() {
		ArrayList<String> result = new ArrayList<>();
		result.add(getHelpHeader());
		result.addAll(getHelpBody());
		return result;
	}

	public default String getHelpHeader() {
		return "Help for " + getName();
	}

	public List<String> getHelpBody();

	public String getSimpleHelp();
}
