package handlebars;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateEngineTest {
    @Test
    @DisplayName("TemplateEngine으로 뷰에 모델 적용")
    void templateEngine() {
        User user = new User("javajigi", "password",
                "자바지기", "javajigi@gmail.com");

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        String profilePage = TemplateEngine.applyModelInView("user/profile", model);

        assertThat(profilePage).contains("자바지기");
        assertThat(profilePage).contains("javajigi@gmail.com");
    }
}