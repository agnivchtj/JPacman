package model.backend;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * This class handles password hashing and verification with Argon2.
 */
public class PasswordHasher {

    private transient Argon2 argon2;

    /**
     * Constructor.
     */
    public PasswordHasher() {

        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    }

    /**
     * Hashes the given password.
     * @param password The password to be hashed
     * @return The hashed password
     */
    public String hashPassword(String password) {

        return argon2.hash(2, 65536, 1, password);

    }

    /**
     * Verifies whether the password and its hashed form match.
     * @param password The password
     * @param hashedPassword The hashed password
     * @return True if the passwords match
     *          False otherwise
     */
    public boolean verifyPassword(String password, String hashedPassword) {

        return argon2.verify(hashedPassword, password);

    }


}
