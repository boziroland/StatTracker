package org.github.boziroland.ui.views;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.Data;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.BiFunction;

@SpringView(name = RegistrationView.NAME)
@SpringComponent
public class RegistrationView extends GridLayout implements View {

	public static final String NAME = "register";

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationView.class);

	@Autowired
	private UserService userService;

	@Data
	private class DummyUser {
		String username = "";
		String password = "";
		String email = "";
		String leagueName = "";
		String owName = "";
	}

	private final Binder<DummyUser> binder = new Binder<>(DummyUser.class);
	private final FormLayout registrationForm = new FormLayout();

	String[] leagueRegions = {"EUNE", "EUW", "BR", "JP", "KR", "LAN", "LAS", "OCE", "NA", "TR", "RU"};
	String[] owRegions = {"EU", "NA", "KR", "CN", "GLOBAL"};

	@PostConstruct
	public void init() {
		setWidth(100.0f, Unit.PERCENTAGE);
		setHeight(100.0f, Unit.PERCENTAGE);

		registrationForm.setSizeUndefined();

		binder.setBean(new DummyUser());

		Button registerButton = new Button("Regisztrálás");

		final TextField usernameField = new TextField("Felhasználónév");
		binder.forField(usernameField).asRequired().bind(DummyUser::getUsername, DummyUser::setUsername);
		registrationForm.addComponent(usernameField);

		final PasswordField passwordField = new PasswordField("Jelszó");
		binder.forField(passwordField).asRequired().bind(DummyUser::getPassword, DummyUser::setPassword);
		registrationForm.addComponent(passwordField);

		final PasswordField passwordConfirmField = new PasswordField("Jelszó (újra)");
		passwordConfirmField.addValueChangeListener(event -> {
			if (!event.getValue().equals(passwordField.getValue())) {
				passwordField.setStyleName("red");
				passwordConfirmField.setStyleName("red");
				registerButton.setEnabled(false);
			} else {
				passwordField.setStyleName("grey");
				passwordConfirmField.setStyleName("grey");
				registerButton.setEnabled(true);
			}
		});
		binder.forField(passwordConfirmField).asRequired();
		registrationForm.addComponent(passwordConfirmField);

		final TextField emailField = new TextField("Email");
		binder.forField(emailField).asRequired().bind(DummyUser::getEmail, DummyUser::setEmail);
		registrationForm.addComponent(emailField);

		final TextField leagueNameField = new TextField("League of Legends név");
		leagueNameField.setPlaceholder("e.g. meshons");
		binder.forField(leagueNameField).bind(DummyUser::getLeagueName, DummyUser::setLeagueName);
		final ComboBox<String> leagueRegionBox = new ComboBox<>("League of Legends régió", List.of(leagueRegions));
		createGameNameField(leagueNameField, leagueRegionBox);

		final TextField overwatchNameField = new TextField("Overwatch név");
		overwatchNameField.setPlaceholder("e.g. Spricsma#21972");
		binder.forField(overwatchNameField).bind(DummyUser::getOwName, DummyUser::setOwName);
		final ComboBox<String> overwatchRegionBox = new ComboBox<>("Overwatch régió", List.of(owRegions));
		createGameNameField(overwatchNameField, overwatchRegionBox);

		TextArea messageField = new TextArea();
		messageField.setEnabled(false);
		messageField.setStyleName("messageColumn");

		BiFunction<String, String, Void> showMessage = (style, message) -> {
			messageField.setStyleName(style);
			messageField.setValue(message);
			return null;
		};

		registerButton.addClickListener(event -> {
			String name = binder.getBean().username;
			String password = binder.getBean().password;
			String email = binder.getBean().email;
			String leagueName = binder.getBean().leagueName.equals("") ? null : binder.getBean().leagueName;
			String owName = binder.getBean().owName.equals("") ? null : binder.getBean().owName;

			try {
				if (leagueName != null) {
					String leagueRegion = leagueRegionBox.getValue();
					if (leagueRegion == null)
						throw new RegistrationException("Nem választottál League of Legends régiót!");
					else
						leagueName += "#" + leagueRegion;
				}
				if (owName != null) {
					String owRegion = overwatchRegionBox.getValue();
					if (owRegion == null)
						throw new RegistrationException("Nem választottál Overwatch régiót!");
					else
						owName += "-" + owRegion;
				}
				var user = userService.register(name, password, email, leagueName, owName);
				if (user.isPresent()) {
					showMessage.apply("happyColumn", "Sikeres regisztráció! Most már be tudsz lépni.");
				} else {
					showMessage.apply("errorColumn", "Ismeretlen hiba keletkezett.");
				}
			} catch (RegistrationException e) {
				showMessage.apply("errorColumn", e.getMessage());
			} catch (Exception e) {
				showMessage.apply("errorColumn", "Ismeretlen hiba keletkezett.");
				LOGGER.error("Ismeretlen hiba");
				e.printStackTrace();
			}
		});

		registrationForm.addComponent(registerButton);

		addComponent(registrationForm);
		addComponent(messageField);
		Button loginButton = new Button("Lépj be!");
		loginButton.addStyleName(ValoTheme.BUTTON_LINK);
		loginButton.addClickListener(event -> {
			getUI().getNavigator().navigateTo("login");
		});

		HorizontalLayout loginSuggestionLayout = new HorizontalLayout(new Label("Van már accountod? "), loginButton);
		addComponent(loginSuggestionLayout);

		setComponentAlignment(registrationForm, Alignment.MIDDLE_CENTER);
		setComponentAlignment(messageField, Alignment.TOP_CENTER);
		setComponentAlignment(loginSuggestionLayout, Alignment.MIDDLE_CENTER);

	}

	private void createGameNameField(TextField nameField, ComboBox<String> regionBox) {
		nameField.addValueChangeListener(event -> {
			if (!nameField.isEmpty())
				regionBox.setStyleName("red");
			else
				regionBox.removeStyleName("red");
		});
		regionBox.addValueChangeListener(event -> {
			if (!nameField.isEmpty()) {
				if (regionBox.getValue() == null)
					regionBox.setStyleName("red");
				else
					regionBox.removeStyleName("red");
			}
		});
		registrationForm.addComponent(nameField);
		registrationForm.addComponent(regionBox);
	}
}
