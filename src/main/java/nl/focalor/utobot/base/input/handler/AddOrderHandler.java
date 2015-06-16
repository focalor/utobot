package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.entity.Order;
import nl.focalor.utobot.base.model.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class AddOrderHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "addorder";

	public static final String[] COMMAND_NICKS = { "order", "orderadd" };

	@Autowired
	private IOrderService orderService;

	public AddOrderHandler() {
		super(COMMAND_NAME, COMMAND_NICKS);
	}

	@Override
	public IResult handleCommand(CommandInput input) {
		Order order = orderService.add(input.getArgument());
		return new ReplyResult("Order (#" + order.getId() + ") added");
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Adds the specificied order");
		helpBody.add("USAGE:");
		helpBody.add("!order <ORDER>");
		helpBody.add("e.g.:");
		helpBody.add("!order raze Sephi");
		return helpBody;
	}

	@Override
	public String getSimpleHelp() {
		return "Adds the specificied order";
	}

}