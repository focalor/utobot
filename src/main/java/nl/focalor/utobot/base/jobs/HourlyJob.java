package nl.focalor.utobot.base.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import nl.focalor.utobot.base.model.service.IOrderService;
import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HourlyJob implements IScheduledJob {
	private static final Logger LOG = LoggerFactory.getLogger(HourlyJob.class);

	private final IUtopiaService utopiaService;
	private final IOrderService orderService;
	private final IBotService botService;
	private final TreeMap<Integer, List<IScheduledJob>> hourlyJobs = new TreeMap<>();

	public HourlyJob(IUtopiaService utopiaService, IOrderService orderService, IBotService botService) {
		super();
		this.utopiaService = utopiaService;
		this.orderService = orderService;
		this.botService = botService;
	}

	public synchronized void addHourlyJob(int hour, IScheduledJob job) {
		if (utopiaService.getHourOfAge() > hour) {
			job.run();
		} else {
			List<IScheduledJob> jobs = hourlyJobs.get(hour);
			if (jobs == null) {
				jobs = new ArrayList<>();
				hourlyJobs.put(hour, jobs);
			}
			jobs.add(job);
		}
	}

	@Override
	public void run() {
		try {
			botService.broadcast(utopiaService.getUtopiaDate().toString(false));
			botService.broadcast(orderService.getAll().map(order -> order.getText()).collect(Collectors.joining("\n")));

			SortedMap<Integer, List<IScheduledJob>> completedJobs = hourlyJobs.headMap(utopiaService.getHourOfAge());
			for (Entry<Integer, List<IScheduledJob>> entry : completedJobs.entrySet()) {
				hourlyJobs.remove(entry.getKey());
				for (IScheduledJob job : entry.getValue()) {
					job.run();
				}
			}
		} catch (RuntimeException ex) {
			LOG.error("Unexpected exception", ex);
		}
	}

}