package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class DeleteOrderHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "delorder";

	public static final String[] COMMAND_NICKS = {"deleteorder", "orderdel", "orderdel"};

	@Autowired
	private IOrderService orderService;

	public DeleteOrderHandler() {
		super(COMMAND_NAME, COMMAND_NICKS);
	}

	@Override
	public IResult handleCommand(CommandInput input) {
		orderService.delete(Long.parseLong(input.getArgument()));
		return new ReplyResult("Order deleted");
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Deletes the specificied order");
		helpBody.add("USAGE:");
		helpBody.add("!delorder <ORDER NUMBER>");
		helpBody.add("e.g.:");
		helpBody.add("!delorder 2");
		return helpBody;
	}

	@Override
	public String getSimpleHelp() {
		return "Deletes the specificied order";
	}

}