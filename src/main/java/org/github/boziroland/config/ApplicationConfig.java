package org.github.boziroland.config;

import org.github.boziroland.services.*;
import org.github.boziroland.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

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


}