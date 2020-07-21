package org.github.boziroland.services.impl;

import org.github.boziroland.services.ISecurityService;
import org.mindrot.jbcrypt.BCrypt;

public class SecurityService implements ISecurityService {

    private final String salt;

    public SecurityService() {
        salt = BCrypt.gensalt();
    }

    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, salt);
    }
}
