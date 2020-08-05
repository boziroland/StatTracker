package org.github.boziroland.services;

/**
 * The ISecurityService interface provides a way to securely hash and check user passwords.
 */
public interface ISecurityService {

	/**
	 * Hashes the password using BCrypt
	 *
	 * @param password The password to hash
	 * @return The hashed password
	 */
	String hashPassword(String password);

	/**
	 * Checks if the password in the parameter tallies in with the hashed password in parameter
	 * @param password The password to check
	 * @param hashedPassword The hashed password
	 * @return True if the passwords tally, false otherwise
	 */
	boolean checkPassword(String password, String hashedPassword);
}
