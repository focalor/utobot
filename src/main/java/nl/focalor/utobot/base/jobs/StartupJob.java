package nl.focalor.utobot.base.jobs;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.base.service.ILongInitialization;
import nl.focalor.utobot.utopia.service.IAttackService;
import nl.focalor.utobot.utopia.service.ISpellService;
import nl.focalor.utobot.utopia.service.IUtopiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupJob implements IStartupJob, Runnable {
	@Autowired
	private IBotService botService;
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private IAttackService attackService;
	@Autowired
	private ISpellService spellService;

	private List<ILongInitialization> longInitializationBeans;
	private CountDownLatch latch;

	@Override
	public void init() {
		latch = new CountDownLatch(longInitializationBeans.size());
	}

	@Override
	public void registerFinishedInitialization(ILongInitialization bean) {
		latch.countDown();
	}

	@Autowired
	// User setter to avoid circular dependencies
	public void setLongInitializationBeans(List<ILongInitialization> longInitializationBeans) {
		this.longInitializationBeans = longInitializationBeans;
	}

	@Override
	public void run() {
		barrier();

		// Do startup
		botService.broadcast(utopiaService.getUtopiaDate().toString());

		// Load attacks and spells
		attackService.findAll().stream().forEach(attack -> attackService.create(attack, false));
		spellService.findAll().stream().forEach(spellCast -> spellService.create(spellCast, false));
	}

	private void barrier() {
		try {
			// Wait till all slow startups are finished
			boolean success = latch.await(5, TimeUnit.MINUTES);
			if (!success) {
				throw new RuntimeException("failed starting server, encountered timeout");
			}
		} catch (InterruptedException ex) {
			throw new RuntimeException("failed starting server", ex);
		}
	}

}
