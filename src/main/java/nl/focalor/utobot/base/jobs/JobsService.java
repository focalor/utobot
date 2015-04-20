package nl.focalor.utobot.base.jobs;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import nl.focalor.utobot.base.service.IBotService;
import nl.focalor.utobot.utopia.service.IUtopiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService implements IJobsService {
	// TODO move to utopia package
	@Autowired
	private IBotService botService;
	@Autowired
	private IUtopiaService utopiaService;
	@Autowired
	private IStartupJob startupJob;

	private final Timer timer;
	private HourlyJob hourlyJob;

	public JobsService() {
		this.timer = new Timer("timedUtoJobs");
	}

	@PostConstruct
	public void init() {
		startupJob.init();
		new Thread(startupJob).start();

		this.hourlyJob = new HourlyJob(utopiaService, botService);
		this.scheduleAction(hourlyJob, utopiaService.getNextHourChange(), 60 * 60 * 1000);
	}

	@Override
	public void scheduleAction(IScheduledJob job, int utopiaHour) {
		hourlyJob.addHourlyJob(utopiaHour, job);
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
	public void scheduleAction(IScheduledJob job, Date firstExecutionDate, long interval) {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				job.run();

			}
		}, firstExecutionDate, interval);
	}
}
