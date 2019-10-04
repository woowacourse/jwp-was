package webserver.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
    @Test
    void printObjectHandlebar() throws IOException {
        Handlebars handlebars = new Handlebars();
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        Template template = handlebars.compileInline("Hello {{name}}!");
        assertThat(template.apply(user)).contains(user.getName());
    }

    @Test
    void userHandlebarHelper() throws IOException {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("addOne", (context, options) -> (Integer) context + 1);

        Map<String, Object> map = new HashMap<>();
        List<User> users = Arrays.asList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("coogie", "giecoo", "김효건 씨", "bal@gmail.com"));
        map.put("users", users);
        Template template = handlebars.compileInline(
                "{{#users}}" +
                        "{{addOne @index}}" +
                        "{{/users}}"
        );
        assertThat(template.apply(map)).contains("12");
    }

    @Test
    void printTemplateHtml() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("plusOne", (context, options) -> (Integer) context + 1);

        Map<String, Object> map = new HashMap<>();
        List<User> users = Arrays.asList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("coogie", "giecoo", "김효건 씨", "bal@gmail.com"));
        map.put("users", users);
        Template template = handlebars.compile("user/list");
        assertThat(template.apply(map)).contains(users.get(0).getName());
        assertThat(template.apply(map)).contains(users.get(1).getName());
    }
}
