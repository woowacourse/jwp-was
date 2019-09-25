package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    private User signupUser;
    private final String USER_ID = "1234";
    private final String USER_PASSWORD = "passw0rd";
    private final String USER_NAME = "heebong";
    private final String USER_EMAIL = "heebong@bongbong.com";

    @BeforeEach
    void setUp() {
        signupUser = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
    }

    @Test
    void login_true() {
        assertTrue(signupUser.isMatchPassword(USER_PASSWORD));
    }

    @Test
    void login_false_password() {
        assertFalse(signupUser.isMatchPassword(USER_PASSWORD + "a"));
    }
}