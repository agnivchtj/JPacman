package model.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordHasherTest {

    private transient PasswordHasher passwordHasher;
    private transient String password;

    @BeforeEach
    void setupTestEnvironment() {

        passwordHasher = new PasswordHasher();
        password = "maverick";

    }

    @Test
    void testConstructor() {
        assertNotNull(passwordHasher);
    }

    @Test
    void testHashPassword() {

        String hashed = passwordHasher.hashPassword(password);

        assertTrue(hashed.startsWith("$argon2id$"));

    }

    @Test
    void verifyHashedPasswordSuccess() {

        String hashed = passwordHasher.hashPassword(password);

        assertTrue(passwordHasher.verifyPassword(password, hashed));

    }

    @Test
    void verifyHashedPasswordFailure() {

        String hashed = passwordHasher.hashPassword(password);

        assertFalse(passwordHasher.verifyPassword("themyscira", hashed));

    }

}
