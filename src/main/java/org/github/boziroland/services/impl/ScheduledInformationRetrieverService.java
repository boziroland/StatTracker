package org.github.boziroland.services.impl;

import lombok.SneakyThrows;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.IAPIService;
import org.github.boziroland.services.IMilestoneService;
import org.github.boziroland.services.IScheduledInformationRetrieverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledInformationRetrieverService implements IScheduledInformationRetrieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledInformationRetrieverService.class);

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

	@Autowired
	private IMilestoneService milestoneService;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void retrieve(User user, IAPIService service, long delay) {
		Runnable task = () -> {
			try {
				LOGGER.info("Getting data for " + user.getName() + " from service " + service.toString().substring(service.toString().lastIndexOf(".")));
				service.requestInformation(user);
				milestoneService.addMilestones(user);
				checkMilestones(user);
			}catch(Exception e){
				e.printStackTrace();
			}
		};
		LOGGER.info("ADDING USER FOR SCHEDULING WITH DELAY " + delay);
		scheduler.scheduleAtFixedRate(task, Constants.INITIAL_DATA_RETRIEVE_DELAY_IN_SECONDS, Constants.DATA_RETRIEVE_DELAY_IN_SECONDS, TimeUnit.SECONDS);
	}

	@SneakyThrows
	@Override
	public void sendEmail(User user, String message) {
		var userEmail = user.getEmail();

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("noreply@stattracker.com");
		simpleMailMessage.setTo(userEmail);
		simpleMailMessage.setSubject("Teljesítmény elérve!");
		simpleMailMessage.setText(message);
		emailSender.send(simpleMailMessage);
	}

	@Override
	public void checkMilestones(User user) {
		if (user != null) {
			var completedMilestones = milestoneService.checkAchievements(user);

			for (var m : completedMilestones) {
				if (user.getSendEmails()) {
					System.out.println("User completed achievement " + m);
					sendEmail(user, "Gratulálok!\n\nTeljesítetted a(z) " + m + " nevű teljesítmény követelményeit!");
				}
				user.getMilestoneNameUserPointMap().remove(m);
			}
		}
	}
}
