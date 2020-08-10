package org.github.boziroland.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.validator.routines.EmailValidator;
import org.github.boziroland.Translator;
import org.passay.*;

/**
 * The ISecurityService interface provides a way to securely hash and check user passwords.
 */
public interface ISecurityService {

	/**
	 * Checks the validity of an email
	 *
	 * @param email The email to check
	 * @return True, if it's a valid email, false otherwise
	 */
	default boolean isValidEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}

	/**
	 * Checks the validity of a username
	 * By default, a username has to be at least 5 characters long
	 *
	 * @param username The username to check
	 * @return True, if the username is valid, false otherwise
	 */
	default boolean isValidUsername(String username) {
		return username.length() >= 5;
	}

	/**
	 * Checks the validity of a password. By default, a password is valid if:
	 * <p>
	 * - It's at least 8 characters long
	 * - Contains at least 1 uppercase letter
	 * - Contains at least 1 lowercase letter
	 * - Contains at least 1 digit
	 * - Contains no whitespace characters
	 *
	 * @param password The password to check
	 * @return True and no message if the password is valid, false with the problems with the password otherwise
	 */
	default Pair<Boolean, String> isValidPassword(String password) {
		PasswordValidator validator = new PasswordValidator(
				new LengthRule(8),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new WhitespaceRule()
		);
		var result = validator.validate(new PasswordData(password));

		if (result.isValid())
			return new ImmutablePair<>(true, "");

		return new ImmutablePair<>(false, "Nem elég erős jelszó:\n" + Translator.getInstance().translate("en", "hu", StringUtils.join(validator.getMessages(result))));
	}

	/**
	 * Hashes the password using BCrypt
	 *
	 * @param password The password to hash
	 * @return The hashed password
	 */
	String hashPassword(String password);

	/**
	 * Checks if the password in the parameter tallies in with the hashed password in parameter
	 *
	 * @param password       The password to check
	 * @param hashedPassword The hashed password
	 * @return True if the passwords tally, false otherwise
	 */
	boolean checkPassword(String password, String hashedPassword);
}
