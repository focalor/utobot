package nl.focalor.utobot.base.jobs;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.model.service.IOrderService;
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
	private IOrderService orderService;
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private IAttackService attackService;
	@Autowired
	private ISpellService spellService;
	@Autowired
	private List<ILongInitialization> longInitializationBeans;
	private CountDownLatch latch;

	@Override
	@PostConstruct
	public void start() {
		latch = new CountDownLatch(longInitializationBeans.size());
		new Thread(this).start();
	}

	@Override
	public void registerFinishedInitialization(ILongInitialization bean) {
		latch.countDown();
	}

	@Override
	public void run() {
		barrier();

		// Do startup
		botService.broadcast(utopiaService.getUtopiaDate().toString());
		List<String> orders = orderService.getAll().map(order -> order.getText()).collect(Collectors.toList());
		if (!orders.isEmpty()) {
			orders.add(0, "Orders:");
			botService.broadcast(orders);
		}

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