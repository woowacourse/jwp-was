package view;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsViewTest {
    @Test
    @DisplayName("Handlebars로 뷰에 모델 적용")
    void templateEngine() {
        User user = new User("javajigi", "password",
                "자바지기", "javajigi@gmail.com");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        View view = new HandlebarsView("user/profile", model);

        String body = new String(view.render());
        assertThat(body).contains("자바지기");
        assertThat(body).contains("javajigi@gmail.com");
    }

    @Test
    @DisplayName("사용자 목록 확인")
    void userList() {
        User user1 = new User("userId1", "password",
                "userName1", "email1@gmail.com");
        User user2 = new User("userId2", "password",
                "userName2", "email2@gmail.com");

        Map<String, Object> model = new HashMap<>();
        List<User> users = Arrays.asList(user1, user2);
        model.put("users", users);
        View view = new HandlebarsView("user/list", model);

        String body = new String(view.render());
        assertThat(body).contains("userId1");
        assertThat(body).contains("userName1");
        assertThat(body).contains("email1@gmail.com");
        assertThat(body).contains("userId2");
        assertThat(body).contains("userName2");
        assertThat(body).contains("email2@gmail.com");
    }
}