package nl.focalor.utobot.base.input.listener;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandLineInputListener extends AbstractInputListener implements ICommandLineInputListener, Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(CommandLineInputListener.class);

	private final Scanner scanner;
	private final String identifier;

	@Autowired
	public CommandLineInputListener(@Value("${bot.name}") String identifier) {
		super();
		this.scanner = new Scanner(System.in);
		this.identifier = identifier;
	}

	@PostConstruct
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				String line = scanner.nextLine();
				IResult result = super.onMessage(identifier, null, line);
				handleResult(result);
			} catch (RuntimeException ex) {
				// Stay in loop
				LOG.error("Unexpected error", ex);
			}
		}
	}

	private void handleResult(IResult result) {
		if (result == NoReplyResult.NO_REPLY) {
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
	public void broadcast(String message) {
		if (StringUtils.isEmpty(message)) {
			return;
		}
		System.out.println(message);
	}

	@Override
	public void setTopic(String topic) {
		System.out.println("New topic: " + topic);
	}
}
