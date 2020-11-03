package controller;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;

class UserControllerTest {

    private static final String userId = "testId";
    private static final String password = "testPW";
    private static final String name = "testName";
    private static final String email = "test%40test.com";

    private final Map<String, String> contents = new HashMap<>();

    @BeforeEach
    void setUp() {
        contents.put("userId", userId);
        contents.put("password", password);
        contents.put("name", name);
        contents.put("email", email);
    }

    @DisplayName("contents를 이용해 정상적으로 User 생성")
    @Test
    void createTest() {
        final User user = UserController.create(contents);
        final User expected = new User(userId, password, name, email);

        assertThat(user)
            .isEqualToComparingFieldByField(expected);
    }

    @DisplayName("contents를 이용해 정상적으로 User 저장")
    @Test
    void createTest_DB() {
        final User user = UserController.create(contents);
        final User savedUser = DataBase.findUserById(userId);

        assertThat(user)
            .isEqualToComparingFieldByField(savedUser);
    }
}