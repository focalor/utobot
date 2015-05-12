package nl.focalor.utobot.base.input.handler;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.ReplyResult;
import org.springframework.stereotype.Component;

/**
 * @author focalor
 */
@Component
public class EvalHandler extends AbstractGenericCommandHandler {
	public static final String COMMAND_NAME = "eval";

	public EvalHandler() {
		super(COMMAND_NAME);
	}

	@Override
	public IResult handleCommand(CommandInput command) {
		ExpressionBuilder builder = new ExpressionBuilder(command.getArgument());
		Expression exp = builder.variables("pi", "e").build().setVariable("pi", Math.PI).setVariable("e", Math.E);
		double result = exp.evaluate();

		return new ReplyResult(Double.toString(result));
	}

	@Override
	public String getSimpleHelp() {
		return "Evaluates an expression. Use '!help eval' for more info";
	}

	@Override
	public List<String> getHelpBody() {
		List<String> helpBody = new ArrayList<String>();
		helpBody.add("Evaluates an expression.");
		helpBody.add("USAGE:");
		helpBody.add("!eval <expression>");
		helpBody.add("e.g.:");
		helpBody.add("!eval 13*24");
		return helpBody;
	}
}
