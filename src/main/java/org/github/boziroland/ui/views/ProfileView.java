package org.github.boziroland.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.server.ClientConnector;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.DataUpdateException;
import org.github.boziroland.services.impl.UserService;
import org.github.boziroland.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.BiFunction;

@SpringView(name = ProfileView.NAME)
@SpringComponent
public class ProfileView extends VerticalLayout implements View {

	public static final String NAME = "profile";

	@Autowired
	private UserService userService;

	VerticalLayout formLayout = new VerticalLayout();

	String[] leagueRegions = {"EUNE", "EUW", "BR", "JP", "KR", "LAN", "LAS", "OCE", "NA", "TR", "RU"};
	String[] owRegions = {"EU", "NA", "KR", "CN", "GLOBAL"};

	private TextField passwordField;
	private TextField passwordConfirmField;
	private TextField leagueNameField;
	private ComboBox<String> leagueRegionBox;
	private TextField overwatchNameField;
	private ComboBox<String> overwatchRegionBox;
	private TextField emailField;
	private CheckBox receiveEmails;
	private CheckBox profilePublic;
	private Button saveButton;
	private TextArea messageField;

	private User user;

	@PostConstruct
	public void init() {

		this.addAttachListener(this::onAttach);

		formLayout.setSizeUndefined();
		passwordField = new PasswordField("Jelszó");
		passwordConfirmField = new PasswordField("Jelszó (újra)");

		leagueNameField = new TextField("League of Legends név");
		leagueNameField.setPlaceholder("e.g. meshons");
		leagueRegionBox = new ComboBox<>("League of Legends régió", List.of(leagueRegions));
		FormUtils.createGameNameField(formLayout, leagueNameField, leagueRegionBox);

		overwatchNameField = new TextField("Overwatch név");
		overwatchNameField.setPlaceholder("e.g. Spricsma#21972");
		overwatchRegionBox = new ComboBox<>("Overwatch régió", List.of(owRegions));
		FormUtils.createGameNameField(formLayout, overwatchNameField, overwatchRegionBox);

		emailField = new TextField("Email cím");
		receiveEmails = new CheckBox("Szeretnék kapni email-eket");
		profilePublic = new CheckBox("Bárki láthatja a profilom");
		saveButton = new Button("Mentés");

		messageField = new TextArea();
		messageField.setEnabled(false);
		messageField.setStyleName("messageColumn");
		messageField.addStyleName("profileMessageBox");

		formLayout.addComponent(passwordField);
		formLayout.addComponent(passwordConfirmField);
		formLayout.addComponent(emailField);
		formLayout.addComponent(receiveEmails);
		formLayout.addComponent(profilePublic);
		formLayout.addComponent(saveButton);
		formLayout.addComponent(messageField);

		addComponent(formLayout);

		for (var c : formLayout)
			formLayout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);

		setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
	}

	private void onAttach(AttachEvent attachEvent) {
		user = ((MainUI) getUI()).getUser();

		if (user == null) {
			getUI().getNavigator().navigateTo(LoginView.NAME);
		} else {
			saveButton.addClickListener(event -> {
				try {
					if (!passwordField.isEmpty()) {
						if (passwordField.getValue().equals(passwordConfirmField.getValue())) {

							passwordField.removeStyleName("red");
							passwordConfirmField.removeStyleName("red");

							userService.updatePassword(user, passwordField.getValue());
						} else {
							passwordField.setStyleName("red");
							passwordConfirmField.setStyleName("red");
							throw new DataUpdateException("A két jelszó nem egyezik!");
						}
					}
					if (!leagueNameField.isEmpty())
						userService.updateLeagueName(user, leagueNameField.getValue() + "#" + leagueRegionBox.getValue());

					if (!overwatchNameField.isEmpty())
						userService.updateOWName(user, overwatchNameField.getValue() + "-" + overwatchRegionBox.getValue());

					if (!emailField.isEmpty())
						userService.updateEmail(user, emailField.getValue());

					userService.updateEmailReceivability(user, receiveEmails.getValue());
					userService.updateProfileVisibility(user, profilePublic.getValue());

					FormUtils.showMessage(messageField, "happyColumn", "Sikeres változtatás!");
				} catch (DataUpdateException e) {
					FormUtils.showMessage(messageField, "errorColumn", e.getMessage());
				}
			});

			receiveEmails.setValue(user.getSendEmails());
			profilePublic.setValue(user.getProfilePublic());
		}
	}

}
