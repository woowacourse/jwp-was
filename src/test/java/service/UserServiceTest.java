package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    private Map<String, String> parameters1;
    private Map<String, String> parameters2;

    UserService userService;

    @BeforeEach
    void setUp() {
        parameters1 = new HashMap<>();
        parameters2 = new HashMap<>();
        parameters1.put("userId", "pododang");
        parameters1.put("password", "password");
        parameters1.put("email", "podo@gmail.com");
        parameters1.put("name", "이인권");

        parameters2.put("userId", "jm");
        parameters2.put("password", "password!");
        parameters2.put("email", "jm@gmail.com");
        parameters2.put("name", "김정민");

        userService = new UserService();
    }

    @Test
    @DisplayName("유저생성테스트")
    void user_service_test() {
        userService.createUser(parameters1);
        userService.createUser(parameters2);

        assertThat(DataBase.findUserById("pododang")).isEqualTo(new User("pododang", "password", "이인권", "podo@gmail.com"));
        assertThat(DataBase.findUserById("jm")).isEqualTo(new User("jm", "password!", "김정민", "jm@gmail.com"));
    }
}
