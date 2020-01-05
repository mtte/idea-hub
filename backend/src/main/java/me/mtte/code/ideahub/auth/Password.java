package me.mtte.code.ideahub.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Provides functions to handle passwords.
 */
public class Password {

    private Password() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateHash(char[] password) {
        return BCrypt.withDefaults().hashToString(14, password);
    }

    public static boolean verifyPassword(char[] password, char[] hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }

}
