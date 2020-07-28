package org.github.boziroland.services;

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
