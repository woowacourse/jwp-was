package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserAssemblerTest {

    private static final String USER_ID = "john123";
    private static final String PASSWORD = "p@ssW0rd";
    private static final String NAME = "john";
    private static final String EMAIL = "john@example.com";

    @Test
    @DisplayName("User 객체 변환")
    void toEntity() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", USER_ID);
        userInfo.put("password", PASSWORD);
        userInfo.put("name", NAME);
        userInfo.put("email", EMAIL);

        assertThat(UserAssembler.toEntity(userInfo).getUserId()).isEqualTo(USER_ID);
        assertThat(UserAssembler.toEntity(userInfo).getPassword()).isEqualTo(PASSWORD);
        assertThat(UserAssembler.toEntity(userInfo).getName()).isEqualTo(NAME);
        assertThat(UserAssembler.toEntity(userInfo).getEmail()).isEqualTo(EMAIL);
    }
}