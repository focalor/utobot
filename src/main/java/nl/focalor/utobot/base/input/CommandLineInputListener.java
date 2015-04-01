package nl.focalor.utobot.base.input;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandLineInputListener extends Thread implements
		ICommandLineInputListener {
	private final Scanner scanner;
	private final IInputListener listener;
	private final String identifier;

	public CommandLineInputListener(IInputListener listener, String identifier) {
		super();
		this.scanner = new Scanner(System.in);
		this.listener = listener;
		this.identifier = identifier;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String line = scanner.nextLine();
				IResult result = listener.onMessage(identifier, line);
				handleResult(result);
			} catch (NoSuchElementException ex) {
				// Stay in loop
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
			throw new UnsupportedOperationException(
					"Don't know how to handle result of type "
							+ result.getClass().getName());
		}
	}

	@Override
	public void broadcastMessage(String message) {
		System.out.println(message);
	}
}
