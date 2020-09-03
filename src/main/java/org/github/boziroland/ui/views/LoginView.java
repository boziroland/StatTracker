package org.github.boziroland.ui.views;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.boziroland.controllers.CommentController;
import org.github.boziroland.exceptions.LoginException;
import org.github.boziroland.services.impl.UserService;
import org.github.boziroland.ui.MainUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.BiFunction;

@SpringView(name = LoginView.NAME)
@SpringComponent
public class LoginView extends GridLayout implements View {

	public static final String NAME = "login";

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginView.class);

	@Autowired
	private UserService userService;

	private final LoginForm loginForm = new LoginForm();
	private TextArea messageField;

	@PostConstruct
	public void init() {
		setWidth(100.0f, Unit.PERCENTAGE);
		setHeight(100.0f, Unit.PERCENTAGE);
		setStyleName("login");

		messageField = new TextArea();
		messageField.setEnabled(false);
		messageField.setStyleName("messageColumn");

		BiFunction<String, String, Void> showMessage = (style, message) -> {
			messageField.setStyleName(style);
			messageField.setValue(message);
			return null;
		};

		loginForm.setUsernameCaption("Email");
		loginForm.addLoginListener(event -> {
			LOGGER.info("Login attempt with email " + event.getLoginParameter("username"));
			try {
				var user = userService.login(event.getLoginParameter("username"), event.getLoginParameter("password"));
				if (user.isPresent()) {
					((MainUI) getUI()).setUser(user.get());
					((MainUI) getUI()).getLoginButton().setVisible(false);
					((MainUI) getUI()).getLogoutButton().setVisible(true);
					((MainUI) getUI()).getRegisterButton().setVisible(false);
					((MainUI) getUI()).getProfileButton().setVisible(true);
					((MainUI) getUI()).getNavigator().navigateTo(MainView.NAME + "/" + user.get().getName());
				} else {
					showMessage.apply("errorColumn", "Ismeretlen hiba keletkezett.");
				}
			} catch (LoginException e) {
				showMessage.apply("errorColumn", e.getMessage());
			} catch (Exception e) {
				showMessage.apply("errorColumn", "Ismeretlen hiba keletkezett.");
				e.printStackTrace();
			}
		});

		Image image = new Image();
		image.setSource(new ThemeResource("images/gameStats.png"));
		image.addStyleName("loginImage");

		addComponent(image);
		setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		addComponent(messageField);
		setComponentAlignment(messageField, Alignment.MIDDLE_CENTER);
	}
}
