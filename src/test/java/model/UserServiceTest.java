package model;

import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserServiceTest {
    @DisplayName("입력한 정보에 맞는 user가 생성되는지 테스트")
    @Test
    void createUserTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "lavine");
        parameters.put("password", "password");
        parameters.put("name", "lavine2");
        parameters.put("email", "lavine@a.com");
        UserService.create(parameters);
        User user = DataBase.findUserById("lavine");

        assertAll(
                () -> assertThat(user.getUserId()).isEqualTo("lavine"),
                () -> assertThat(user.getPassword()).isEqualTo("password"),
                () -> assertThat(user.getName()).isEqualTo("lavine2"),
                () -> assertThat(user.getEmail()).isEqualTo("lavine@a.com")
        );
    }
}
