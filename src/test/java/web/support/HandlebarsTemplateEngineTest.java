package web.support;

import domain.model.User;
import org.junit.jupiter.api.Test;
import webserver.response.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsTemplateEngineTest {
    @Test
    void render() {
        User user1 = new User("kingbbodae", "password1", "kingbbodae", "kingbbodae@woowa.com");
        User user2 = new User("user2", "password2", "name2", "email@woowa.com");

        Map<String, Object> model = new HashMap<>();
        model.put("users", Arrays.asList(user1, user2));

        ModelAndView modelAndView = new ModelAndView("/user/list.html", model);

        assertThat(new HandlebarsTemplateEngine("/templates", "").render(modelAndView))
                .contains(user1.getName(), user2.getName());
    }
}