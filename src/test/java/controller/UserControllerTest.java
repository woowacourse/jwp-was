package controller;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import java.util.HashMap;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    final String userId = "testId";
    final String password = "testPW";
    final String name = "testName";
    final String email = "test%40test.com";

    @DisplayName("contents를 이용해 정상적으로 User 생성")
    @Test
    void createTest() {
        final Map<String, String> contents = new HashMap<>();
        contents.put("userId", userId);
        contents.put("password", password);
        contents.put("name", name);
        contents.put("email", email);

        final User user = UserController.create(contents);
        final User expected = new User(userId, password, name, email);

        assertThat(user)
            .isEqualToComparingFieldByField(expected);
    }

    @DisplayName("contents를 이용해 정상적으로 User 저장")
    @Test
    void createTest_DB() {
        final Map<String, String> contents = new HashMap<>();
        contents.put("userId", userId);
        contents.put("password", password);
        contents.put("name", name);
        contents.put("email", email);

        final User user = UserController.create(contents);
        final User savedUser = DataBase.findUserById(userId);

        assertThat(user)
            .isEqualToComparingFieldByField(savedUser);
    }
}