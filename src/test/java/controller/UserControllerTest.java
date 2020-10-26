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

    @DisplayName("queryParams를 이용해 정상적으로 User 생성")
    @Test
    void createTest() {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userId);
        queryParams.put("password", password);
        queryParams.put("name", name);
        queryParams.put("email", email);

        final User user = UserController.create(queryParams);
        final User expected = new User(userId, password, name, email);

        assertThat(user)
            .isEqualToComparingFieldByField(expected);
    }

    @DisplayName("queryParams를 이용해 정상적으로 User 저장")
    @Test
    void createTest_DB() {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", userId);
        queryParams.put("password", password);
        queryParams.put("name", name);
        queryParams.put("email", email);

        final User user = UserController.create(queryParams);
        final User savedUser = DataBase.findUserById(userId);

        assertThat(user)
            .isEqualToComparingFieldByField(savedUser);
    }
}