package org.github.boziroland.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.github.boziroland.entities.User;
import org.github.boziroland.ui.views.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SpringUI
@SpringViewDisplay
@Theme("mytheme")
@PreserveOnRefresh
@Data
public class MainUI extends UI implements ViewDisplay {

	private User user;

	private Panel springViewDisplay;

	@Autowired
	private SpringNavigator navigator;

	private Label mainLabel;
	private Button mainButton;
	private Button loginButton;
	private Button logoutButton;
	private Button registerButton;
	private Button searchButton;
	private Button profileButton;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setNavigator(navigator);
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setStyleName("layoutPadding");
		setContent(layout);
		CssLayout navBar = initializeNavbar();

		layout.addComponent(navBar);

		springViewDisplay = new Panel();
		springViewDisplay.addStyleName("grey");
		springViewDisplay.setSizeFull();
		layout.addComponent(springViewDisplay);
		layout.setExpandRatio(springViewDisplay, 1.0f);

		getNavigator().setErrorView(DefaultView.class);
	}

	private CssLayout initializeNavbar(){
		CssLayout navBar = new CssLayout();

		initializeButtons();

		navBar.setStyleName("navBarSpacing");
		navBar.addComponent(mainButton);
		navBar.addComponent(loginButton);
		navBar.addComponent(logoutButton);
		navBar.addComponent(mainLabel);
		navBar.addComponent(registerButton);
		navBar.addComponent(searchButton);
		navBar.addComponent(profileButton);

		for (Component component : navBar)
			component.addStyleName("addSpacingToNavBarElements");

		return navBar;
	}

	private void initializeButtons() {
		mainLabel = new Label("Game Statistics Tracker");
		mainLabel.setStyleName("mainLabelPadding");

		mainButton = new Button("Főoldal", clickEvent -> getNavigator().navigateTo(MainView.NAME));
		loginButton = new Button("Belépés", clickEvent -> getNavigator().navigateTo(LoginView.NAME));
		registerButton = new Button("Regisztráció", clickEvent -> getNavigator().navigateTo(RegistrationView.NAME));
		profileButton = new Button("Beállítások", clickEvent -> getNavigator().navigateTo(ProfileView.NAME));
		searchButton = new Button("Keresés", clickEvent -> getNavigator().navigateTo(SearchView.NAME));
		profileButton.setVisible(false);
		logoutButton = new Button("Kijelentkezés", clickEvent -> {
			user = null;
			loginButton.setVisible(true);
			registerButton.setVisible(true);
			logoutButton.setVisible(false);
			profileButton.setVisible(false);
			getNavigator().navigateTo(LoginView.NAME);
		});
		logoutButton.setVisible(false);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}
}
