package org.github.boziroland.ui.views;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@SpringView(name = RegistrationView.NAME)
public class RegistrationView extends GridLayout implements View {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationView.class);

    public static final String NAME = "register";

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

    @PostConstruct
    public void init() {
        setWidth(100.0f, Unit.PERCENTAGE);
        setHeight(100.0f, Unit.PERCENTAGE);

        registrationForm.setSizeUndefined();

        binder.setBean(new DummyUser());

        Button registerButton = new NativeButton("Regisztrálás");

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
        registrationForm.addComponent(leagueNameField);

        final TextField overwatchNameField = new TextField("Overwatch név");
        registrationForm.addComponent(overwatchNameField);

        registrationForm.addComponent(registerButton);

        addComponent(registrationForm);
        setComponentAlignment(registrationForm, Alignment.MIDDLE_CENTER);

    }
}
