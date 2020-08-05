package org.github.boziroland.services.impl;

import org.github.boziroland.services.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityService implements ISecurityService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean checkPassword(String password, String hashedPassword) {
		return passwordEncoder.matches(password, hashedPassword);
	}
}
