package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.BaseSettings;
import nl.focalor.utobot.base.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class LinkHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "link";

	private final Map<String, Link> links;

	@Autowired
	public LinkHandler(BaseSettings baseSettings) {
		super(COMMAND_NAME);
		links = new HashMap<>();
		//@formatter:off
		baseSettings.getLinks().stream().forEach(
			link -> link.getIds().stream().forEach(
			id -> links.put(id, link))
		);
		//@formatter:on
	}

	@Override
	public IResult handleCommand(CommandInput command) {
		Link link = links.get(command.getArgument());
		if (link == null) {
			return new ReplyResult("No link found");
		} else {
			return new ReplyResult(link.getLink());
		}
	}

	@Override
	public String getSimpleHelp() {
		return "Shows the requested link.";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Shows the requested link.");
		helpBody.add("USAGE:");
		helpBody.add("!link <id>");
		helpBody.add("e.g.:");
		helpBody.add("!link throne");
		return helpBody;
	}
}