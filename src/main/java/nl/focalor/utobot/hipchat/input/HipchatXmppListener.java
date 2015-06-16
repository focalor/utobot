package nl.focalor.utobot.hipchat.input;

import nl.focalor.utobot.base.input.ErrorResult;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.MultiReplyResult;
import nl.focalor.utobot.base.input.NoReplyResult;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.listener.AbstractInputListener;
import nl.focalor.utobot.hipchat.model.HipchatSettings;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.xmpp.core.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.roster.RosterManager;
import rocks.xmpp.core.roster.model.Contact;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.stanza.model.AbstractMessage.Type;
import rocks.xmpp.core.stanza.model.client.Message;

/**
 * @author focalor
 */

@Component
public class HipchatXmppListener extends AbstractInputListener implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(HipchatXmppListener.class);

	private final String username;
	private final String password;

	private XmppSession session;

	@Autowired
	public HipchatXmppListener(HipchatSettings settings) {
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
					Jid fromJid = event.getMessage().getFrom();
					Contact contact = rosterManager.getContact(fromJid);

					IResult result = onMessage(null, contact.getName(), msg);

					if (result == NoReplyResult.NO_REPLY) {
					} else if (result instanceof ErrorResult) {
						send(fromJid, ((ErrorResult) result).getMessage());
					} else if (result instanceof ReplyResult) {
						send(fromJid, ((ReplyResult) result).getMessage());
					} else if (result instanceof MultiReplyResult) {
						MultiReplyResult res = (MultiReplyResult) result;
						send(fromJid, StringUtils.join(res.getMessages(), "\n"));
					} else {
						throw new UnsupportedOperationException("Don't know how to handle result of type "
								+ result.getClass().getName());
					}
				}
			});
		} catch (XmppException ex) {
			throw new RuntimeException("Unexpected exception", ex);
		}
	}

	private void send(Jid toJid, String msg) {
		session.send(new Message(toJid, Type.CHAT, msg));
	}

	@Override
	public void finalize() throws Throwable {
		try {
			session.close();
		} catch (Exception ex) {
			LOG.error("Unexpected error", ex);
		}
		super.finalize();
	}
}
