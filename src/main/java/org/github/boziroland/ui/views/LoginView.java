package org.github.boziroland.ui.views;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.VerticalLayout;
import org.github.boziroland.controllers.CommentController;
import org.github.boziroland.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = LoginView.NAME)
public class LoginView extends GridLayout implements View {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);

	public static final String NAME = "login";

	@Autowired
	private UserService userService;

	LoginForm loginForm = new LoginForm();

	@PostConstruct
	public void init(){
		setWidth(100.0f, Unit.PERCENTAGE);
		setHeight(100.0f, Unit.PERCENTAGE);
		setStyleName("login");

		loginForm.setUsernameCaption("Email");
		loginForm.addLoginListener(event -> {
			LOGGER.info("Login attempt with username " + event.getLoginParameter("username"));
			userService.login(event.getLoginParameter("username"), event.getLoginParameter("password"));
		});

		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

	}














}
