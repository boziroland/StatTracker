package org.github.boziroland.services.impl;

import org.github.boziroland.entities.User;
import org.github.boziroland.services.IAPIService;
import org.github.boziroland.services.IScheduledInformationRetrieverService;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledInformationRetrieverService implements IScheduledInformationRetrieverService {

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

	private LocalTime retrieveTime;

	@Override
	public void retrieve(User user, IAPIService service) {
		Runnable sender = new Runnable() {
			public void run() {
				service.requestInformation(user);
			}
		};
		long delay = ChronoUnit.SECONDS.between(LocalTime.now(), retrieveTime);
		scheduler.schedule(sender, delay, TimeUnit.SECONDS);
	}

	public void setRetrieveTime(LocalTime retrieveTime) {
		this.retrieveTime = retrieveTime;
	}

	public LocalTime getRetrieveTime() {
		return retrieveTime;
	}
}
