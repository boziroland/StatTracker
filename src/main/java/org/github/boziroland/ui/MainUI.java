package org.github.boziroland.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import org.github.boziroland.ui.views.DefaultView;
import org.github.boziroland.ui.views.LoginView;
import org.github.boziroland.ui.views.RegistrationView;
import org.github.boziroland.ui.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
@Theme("mytheme")
public class MainUI extends UI implements ViewDisplay {

	private Panel springViewDisplay;

	@Autowired
	private SpringNavigator navigator;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setNavigator(navigator);
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setStyleName("layoutPadding");
		setContent(layout);
		CssLayout navBar = new CssLayout();
		navBar.setStyleName("navBarSpacing");
		navBar.addComponent(new Label("Game Statistics Tracker"));
		navBar.addComponent(new Button("uwu click me daddy", clickEvent -> getNavigator().navigateTo(MainView.NAME)));
		navBar.addComponent(new Button("uwu click me to log in daddy", clickEvent -> getNavigator().navigateTo(LoginView.NAME)));
		navBar.addComponent(new Button("uwu click me to register daddy", clickEvent -> getNavigator().navigateTo(RegistrationView.NAME)));

		for (Component component : navBar)
			component.setStyleName("addSpacingToNavBarElements");

		layout.addComponent(navBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		layout.addComponent(springViewDisplay);
		layout.setExpandRatio(springViewDisplay, 1.0f);

		getNavigator().setErrorView(DefaultView.class);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}
}
