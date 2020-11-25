package utils;

import domain.user.User;
import domain.user.dto.UserListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TemplateUtilsTest {

    @DisplayName("TemplateUtils.create 기능 테스트")
    @Test
    void create() {
        String userId = "1";
        String password = "password";
        String userName = "dundung";
        String email = "dundung@gmail.com";
        List<User> users = Arrays.asList(new User(userId, password, userName, email));
        UserListResponse userListResponse = new UserListResponse(users);

        String template = TemplateUtils.create("user/list", userListResponse);

        assertAll(() -> {
            assertThat(template.contains(userName)).isTrue();
            assertThat(template.contains(email)).isTrue();
        });
    }
}