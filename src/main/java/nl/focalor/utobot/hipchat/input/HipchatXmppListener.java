package nl.focalor.utobot.hipchat.input;

import nl.focalor.utobot.hipchat.model.HipchatSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.roster.RosterManager;
import rocks.xmpp.core.roster.model.Contact;
import rocks.xmpp.core.session.XmppSession;

/**
 * @author focalor
 */

@Component
public class HipchatXmppListener implements Runnable {
	private final String username;
	private final String password;
	private final IHipchatInputListener listener;

	private XmppSession session;

	@Autowired
	public HipchatXmppListener(HipchatSettings settings, IHipchatInputListener listener) {
		this.listener = listener;
		this.username = settings.getXmppUser();
		this.password = settings.getXmppPassword();

		if (settings.isXmppActive()) {
			new Thread(this).start();
		}
	}

	@Override
	public void run() {
		try {
			session = new XmppSession("chat.hipchat.com");
			session.connect();
			session.login(username, password, "xmpp");

			session.addInboundMessageListener(event -> {
				String msg = event.getMessage().getBody();
				if (msg != null) {
					RosterManager rosterManager = session.getManager(RosterManager.class);
					Contact contact = rosterManager.getContact(event.getMessage().getFrom());
					listener.onMessage(null, contact.getName(), msg);
				}
			});
		} catch (XmppException ex) {
			throw new RuntimeException("Unexpected exception", ex);
		}
	}

	@Override
	public void finalize() throws Throwable {
		try {
			session.close();
		} catch (Exception ex) {
			// TODO log
		}
		super.finalize();
	}
}
