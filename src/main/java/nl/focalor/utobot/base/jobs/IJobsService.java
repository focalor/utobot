package nl.focalor.utobot.base.jobs;

import java.util.Date;

public interface IJobsService {

	public void scheduleAction(IScheduledJob job, Date executionDate);

	public void scheduleAction(IScheduledJob job, Date firstExecutionDate,
			long interval);
}
