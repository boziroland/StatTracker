package org.github.boziroland.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import org.github.boziroland.entities.User;
import org.github.boziroland.ui.views.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
@Theme("mytheme")
public class MainUI extends UI implements ViewDisplay {

	private User user;

	private Panel springViewDisplay;

	@Autowired
	private SpringNavigator navigator;

	private Button mainButton;
	private Button loginButton;
	private Button logoutButton;
	private Button registerButton;
	private Button searchButton;
	private Button profileButton;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setNavigator(navigator);
		initalizeButtons();
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setStyleName("layoutPadding");
		setContent(layout);
		CssLayout navBar = new CssLayout();
		navBar.setStyleName("navBarSpacing");
		navBar.addComponent(new Label("Game Statistics Tracker"));
		navBar.addComponent(mainButton);
		navBar.addComponent(loginButton);
		navBar.addComponent(logoutButton);
		navBar.addComponent(registerButton);
		navBar.addComponent(searchButton);
		navBar.addComponent(profileButton);

		for (Component component : navBar)
			component.setStyleName("addSpacingToNavBarElements");

		layout.addComponent(navBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		layout.addComponent(springViewDisplay);
		layout.setExpandRatio(springViewDisplay, 1.0f);

		getNavigator().setErrorView(DefaultView.class);
	}

	private void initalizeButtons() {
		mainButton = new Button("uwu click me daddy", clickEvent -> getNavigator().navigateTo(MainView.NAME));
		loginButton = new Button("uwu click me to log in daddy", clickEvent -> getNavigator().navigateTo(LoginView.NAME));
		registerButton = new Button("uwu click me to register daddy", clickEvent -> getNavigator().navigateTo(RegistrationView.NAME));
		searchButton = new Button("uwu click me to search daddy", clickEvent -> getNavigator().navigateTo(SearchView.NAME));
		profileButton = new Button("uwu click me to edit ur profile daddy", clickEvent -> getNavigator().navigateTo(ProfileView.NAME));
		logoutButton = new Button("uwu click me to log out daddy", clickEvent -> {user = null; loginButton.setVisible(true); registerButton.setVisible(true); logoutButton.setVisible(false); });
		logoutButton.setVisible(false);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}

	public Button getRegisterButton() {
		return registerButton;
	}

	public void setRegisterButton(Button registerButton) {
		this.registerButton = registerButton;
	}

	public Button getLogoutButton() {
		return logoutButton;
	}

	public void setLogoutButton(Button logoutButton) {
		this.logoutButton = logoutButton;
	}
}
