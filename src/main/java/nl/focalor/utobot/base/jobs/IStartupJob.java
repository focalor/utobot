package nl.focalor.utobot.base.jobs;

import nl.focalor.utobot.base.service.ILongInitialization;

/**
 * @author focalor
 */
public interface IStartupJob extends IScheduledJob {
	public void init();

	public void registerFinishedInitialization(ILongInitialization bean);
}
