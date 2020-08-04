package org.github.boziroland.exceptions;

//TODO: Ez láthatóan nincs használva, ha nincs rá szükség akkor nyugodtan töröljük.
public class LoginException extends RuntimeException {

	public LoginException() {
		super();
	}

	public LoginException(String message) {
		super(message);
	}
}
