package webserver.support;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsTemplateEngineTest {
    @Test
    @DisplayName("Handlebars를 이용하여 문자열로 변환한다.")
    void apply() {
        TemplateEngine templateEngine = new HandlebarsTemplateEngine("user/list");
        Map<String, Object> users = new HashMap<>();

        User user = User.of("testId", "testPassword", "testName", "testEmail");
        Database.addUser(user);

        users.put("users", Database.findAll());

        assertThat(templateEngine.apply("user/list", users).get()).contains("testName");
    }
}