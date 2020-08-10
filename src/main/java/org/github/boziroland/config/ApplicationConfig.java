package org.github.boziroland.config;

import lombok.SneakyThrows;
import org.github.boziroland.services.*;
import org.github.boziroland.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

	@Bean
	public IOverwatchService overwatchService() {
		return new OverwatchService();
	}

	@Bean
	public ICommentService commentService() {
		return new CommentService();
	}

	@Bean
	public ILeagueService leagueService() throws IOException {
		return new LeagueService();
	}

	@Bean
	public IMilestoneService milestoneService() {
		return new MilestoneService();
	}

	@Bean
	public IUserService userService() {
		return new UserService();
	}

	@Bean
	public ISecurityService securityService() {
		return new SecurityService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@SneakyThrows
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		FileReader reader = new FileReader("src/main/resources/properties/email.properties");
		Properties p = new Properties();
		p.load(reader);

		mailSender.setUsername(p.getProperty("username"));
		mailSender.setPassword(p.getProperty("password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}