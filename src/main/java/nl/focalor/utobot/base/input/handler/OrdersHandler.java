package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.model.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class OrdersHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "orders";

	@Autowired
	private IOrderService orderService;

	public OrdersHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput input) {
		MultiReplyResult result = new MultiReplyResult(orderService.getAll()
				.map(order -> order.getId() + ": " + order.getText()).collect(Collectors.toList()));

		if (result.getMessages().isEmpty()) {
			return new ReplyResult("No orders found");
		} else {
			return result;
		}
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Shows the current orders");
		helpBody.add("USAGE:");
		helpBody.add("!orders");
		return helpBody;
	}

	@Override
	public String getSimpleHelp() {
		return "Shows the current orders";
	}

}