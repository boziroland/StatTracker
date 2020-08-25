package org.github.boziroland.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.boziroland.exceptions.DataUpdateException;
import org.github.boziroland.services.impl.UserService;
import org.github.boziroland.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.BiFunction;

@SpringView(name = ProfileView.NAME)
@SpringComponent
public class ProfileView extends VerticalLayout implements View {

	public static final String NAME = "profile";

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();
		TextField passwordField = new PasswordField("Jelszó");
		TextField passwordAgainField = new PasswordField("Jelszó (újra)");
		TextField leagueNameField = new TextField("League of Legends név");
		TextField owNameField = new TextField("Overwatch név");
		TextField emailField = new TextField("Email cím");
		CheckBox receiveEmails = new CheckBox("Szeretnék kapni email-eket", true);
		CheckBox profilePublic = new CheckBox("Bárki láthatja a profilom", true);
		Button saveButton = new Button("Mentés");

		TextArea messageField = new TextArea();
		messageField.setEnabled(false);

		BiFunction<String, String, Void> showMessage = (style, message) -> {
			messageField.setStyleName(style);
			messageField.setValue(message);
			return null;
		};

		saveButton.addClickListener(event -> {
			var user = ((MainUI) getUI()).getUser();
			try {
				if (!passwordField.isEmpty()) {
					if (passwordField.getValue().equals(passwordAgainField.getValue())) {
						passwordField.setStyleName("grey");
						passwordAgainField.setStyleName("grey");

						userService.updatePassword(user, passwordField.getValue());

					} else {
						passwordField.setStyleName("red");
						passwordAgainField.setStyleName("red");
						throw new DataUpdateException("A két jelszó nem egyezik!");
					}
				}
				if (!leagueNameField.isEmpty())
					userService.updateLeagueName(user, leagueNameField.getValue());

				if (!owNameField.isEmpty())
					userService.updateOWName(user, owNameField.getValue());

				if (!emailField.isEmpty())
					userService.updateEmail(user, emailField.getValue());

				userService.updateEmailReceivability(user, receiveEmails.getValue());
				userService.updateProfileVisibility(user, profilePublic.getValue());

				showMessage.apply("happyColumn", "Sikeres változtatás!");
			} catch (DataUpdateException e) {
				showMessage.apply("errorColumn", e.getMessage());
			}
		});

		verticalLayout.addComponent(passwordField);
		verticalLayout.addComponent(passwordAgainField);
		verticalLayout.addComponent(leagueNameField);
		verticalLayout.addComponent(owNameField);
		verticalLayout.addComponent(emailField);
		verticalLayout.addComponent(receiveEmails);
		verticalLayout.addComponent(profilePublic);
		verticalLayout.addComponent(saveButton);
		verticalLayout.addComponent(messageField);

		addComponent(verticalLayout);
		setComponentAlignment(verticalLayout, Alignment.MIDDLE_CENTER);
	}

}
