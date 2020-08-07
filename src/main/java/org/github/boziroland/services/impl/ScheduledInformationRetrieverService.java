package org.github.boziroland.services.impl;

import org.github.boziroland.Constants;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.IAPIService;
import org.github.boziroland.services.IScheduledInformationRetrieverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledInformationRetrieverService implements IScheduledInformationRetrieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledInformationRetrieverService.class);

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

	@Override
	public void retrieve(User user, IAPIService service, long delay) {

		Runnable sender = new Runnable() {
			public void run() {
				LOGGER.info("Getting data for " + user.getName() + " from service " + service.toString().substring(service.toString().lastIndexOf(".")));
				service.requestInformation(user);
			}
		};
		scheduler.scheduleAtFixedRate(sender, Constants.INITIAL_DATA_RETRIEVE_DELAY_IN_SECONDS, delay, TimeUnit.SECONDS);
	}
}
