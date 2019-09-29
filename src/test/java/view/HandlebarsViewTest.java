package view;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
}