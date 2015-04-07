package nl.focalor.utobot.base.input.listener;

import java.util.Scanner;
import javax.annotation.PostConstruct;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInputListener extends Thread implements ICommandLineInputListener {
	private static final Logger LOG = LoggerFactory.getLogger(CommandLineInputListener.class);

	private final Scanner scanner;
	private IInputListener listener;
	private final String identifier;

	@Autowired
	public CommandLineInputListener(@Value("${bot.name}") String identifier) {
		super();
		this.scanner = new Scanner(System.in);
		this.identifier = identifier;
	}

	@Autowired
	public void setListener(IInputListener listener) {
		this.listener = listener;
	}

	@Override
	@PostConstruct
	public void start() {
		super.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				String line = scanner.nextLine();
				IResult result = listener.onMessage(identifier, line);
				handleResult(result);
			} catch (RuntimeException ex) {
				// Stay in loop
				LOG.error("", ex);
			}
		}
	}

	private void handleResult(IResult result) {
		if (result == null) {
			// Ignore unknown commands
		} else if (result instanceof ReplyResult) {
			ReplyResult res = (ReplyResult) result;
			System.out.println(res.getMessage());
		} else if (result instanceof MultiReplyResult) {
			MultiReplyResult res = (MultiReplyResult) result;
			for (String msg : res.getMessages()) {
				System.out.println(msg);
			}
		} else {
			throw new UnsupportedOperationException("Don't know how to handle result of type "
					+ result.getClass().getName());
		}
	}

	@Override
	public void broadcastMessage(String message) {
		System.out.println(message);
	}
}
