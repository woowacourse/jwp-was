package handlebars;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateEngineTest {
    @Test
    @DisplayName("TemplateEngine으로 뷰에 모델 적용")
    void templateEngine() throws Exception {
        User user = new User("javajigi", "password",
                "자바지기", "javajigi@gmail.com");
        String profilePage = TemplateEngine.applyModelInView("user/profile", user);

        assertThat(profilePage).contains("자바지기");
        assertThat(profilePage).contains("email");
    }
}