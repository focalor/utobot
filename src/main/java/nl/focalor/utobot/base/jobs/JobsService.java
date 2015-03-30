package nl.focalor.utobot.base.jobs;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService implements IJobsService {
	@Autowired
	private IUtopiaService utopiaService;

	private final Timer timer;

	public JobsService() {
		this.timer = new Timer("timedUtoJobs");
	}

	@PostConstruct
	public void init() {
		this.scheduleAction(new StartupJob(), new Date());
		this.scheduleAction(new HourlyJob(), utopiaService.getNextHourChange(),
				60 * 60 * 1000);
	}

	@Override
	public void scheduleAction(IScheduledJob job, Date executionDate) {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				job.run();

			}
		}, executionDate);
	}

	@Override
	public void scheduleAction(IScheduledJob job, Date firstExecutionDate,
			long interval) {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				job.run();

			}
		}, firstExecutionDate, interval);
	}
}
