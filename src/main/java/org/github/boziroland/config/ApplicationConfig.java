package org.github.boziroland.config;

import org.github.boziroland.DAL.*;
import org.github.boziroland.DAL.impl.*;
import org.github.boziroland.services.*;
import org.github.boziroland.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ApplicationConfig {

	@Bean
	public IAPIData1DAO apiData1DAO() {
		return new APIData1InMemory();
	}

	@Bean
	public ICommentDAO commentDAO() {
		return new CommentInMemory();
	}

	@Bean
	public ILeagueDAO leagueDAO() {
		return new LeagueDataInMemory();
	}

	@Bean
	public IMilestoneDAO milestoneDAO() {
		return new MilestoneInMemory();
	}

	@Bean
	public IUserDAO userDAO() {
		return new UserInMemory();
	}

	@Bean
	public IOverwatchService apiData1Service(IAPIData1DAO apiData1DAO) {
		return new OverwatchService();
	}

	@Bean
	public ICommentService commentService(ICommentDAO commentDAO) {
		return new CommentService();
	}

	@Bean
	public ILeagueService leagueService(ILeagueDAO leagueDAO) throws IOException {
		return new LeagueService();
	}

	@Bean
	public IMilestoneService milestoneService(IMilestoneDAO milestoneDAO) {
		return new MilestoneService();
	}

	@Bean
	public IUserService userService(IUserDAO userDAO) {
		return new UserService();
	}

	@Bean
	public IOverwatchService apiData1Service() {
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
}