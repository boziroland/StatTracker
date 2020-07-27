package org.github.boziroland.services;

public interface ISecurityService {

	/**
	 * Hashes the password using BCrypt
	 *
	 * @param password The password to hash
	 * @return The hashed password
	 */
	String hashPassword(String password);
}
