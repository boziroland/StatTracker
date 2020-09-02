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

	String[] leagueRegions = { "EUNE", "EUW", "BR", "JP", "KR", "LAN", "LAS", "OCE", "NA", "TR", "RU" };
	String[] owRegions = { "EU", "US", "KR", "CN", "GLOBAL" };

	private TextField usernameField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmField;
	private TextField emailField;
	private TextField leagueNameField;
	private ComboBox<String> leagueRegionBox;
	private TextField overwatchNameField;
	private ComboBox<String> overwatchRegionBox;
	private TextArea messageField;
	private Button loginButton;

	@PostConstruct
	public void init() {
		setWidth(100.0f, Unit.PERCENTAGE);
		setHeight(100.0f, Unit.PERCENTAGE);

		registrationForm.setSizeUndefined();

		binder.setBean(new DummyUser());

		Button registerButton = new Button("Regisztrálás");

		usernameField = new TextField("Felhasználónév");
		binder.forField(usernameField).asRequired().bind(DummyUser::getUsername, DummyUser::setUsername);
		registrationForm.addComponent(usernameField);

		passwordField = new PasswordField("Jelszó");
		binder.forField(passwordField).asRequired().bind(DummyUser::getPassword, DummyUser::setPassword);
		registrationForm.addComponent(passwordField);

		passwordConfirmField = new PasswordField("Jelszó (újra)");
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

		emailField = new TextField("Email");
		binder.forField(emailField).asRequired().bind(DummyUser::getEmail, DummyUser::setEmail);
		registrationForm.addComponent(emailField);

		leagueNameField = new TextField("League of Legends név");
		leagueNameField.setPlaceholder("e.g. meshons");
		binder.forField(leagueNameField).bind(DummyUser::getLeagueName, DummyUser::setLeagueName);
		leagueRegionBox = new ComboBox<>("League of Legends régió", List.of(leagueRegions));
		FormUtils.createGameNameField(registrationForm, leagueNameField, leagueRegionBox);

		overwatchNameField = new TextField("Overwatch név");
		overwatchNameField.setPlaceholder("e.g. Spricsma#21972");
		binder.forField(overwatchNameField).bind(DummyUser::getOwName, DummyUser::setOwName);
		overwatchRegionBox = new ComboBox<>("Overwatch régió", List.of(owRegions));
		FormUtils.createGameNameField(registrationForm, overwatchNameField, overwatchRegionBox);

		messageField = new TextArea();
		messageField.setEnabled(false);
		messageField.setStyleName("messageColumn");

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
					FormUtils.showMessage(messageField, "happyColumn", "Sikeres regisztráció! Most már be tudsz lépni.");
				} else {
					FormUtils.showMessage(messageField, "errorColumn", "Ismeretlen hiba keletkezett.");
				}
			} catch (RegistrationException e) {
				FormUtils.showMessage(messageField, "errorColumn", e.getMessage());
			} catch (Exception e) {
				FormUtils.showMessage(messageField, "errorColumn", "Ismeretlen hiba keletkezett.");
				LOGGER.error("Ismeretlen hiba");
				e.printStackTrace();
			}
		});

		registrationForm.addComponent(registerButton);

		addComponent(registrationForm);
		addComponent(messageField);

		loginButton = new Button("Lépj be!");
		loginButton.addStyleName(ValoTheme.BUTTON_LINK);
		loginButton.addClickListener(event -> {
			getUI().getNavigator().navigateTo("login");
		});

		Label alreadyHaveAnAccountLabel = new Label("Van már accountod? ");
		alreadyHaveAnAccountLabel.addStyleName("alreadyRegisteredTextPadding");
		HorizontalLayout loginSuggestionLayout = new HorizontalLayout(alreadyHaveAnAccountLabel, loginButton);
		addComponent(loginSuggestionLayout);

		setComponentAlignment(registrationForm, Alignment.MIDDLE_CENTER);
		setComponentAlignment(messageField, Alignment.TOP_CENTER);
		setComponentAlignment(loginSuggestionLayout, Alignment.MIDDLE_CENTER);
	}

}
