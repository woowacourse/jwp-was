package webserver.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("coogie", (context, options) -> {

            return null;
        });

        Template template = handlebars.compile("user/list");

        List<User> users = Arrays.asList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("coogie", "giecoo", "김효건 씨", "bal@gmail.com"));
        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        String profilePage = template.apply(map);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void printHandlebar() throws IOException {
        Handlebars handlebars = new Handlebars();
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        Template template = handlebars.compileInline("Hello {{name}}!");
        System.out.println(template.apply(user));
    }

    @Test
    void q() throws IOException {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("coogie", (context, options) -> (Integer) context + 1);

        Map<String, Object> map = new HashMap<>();
        List<User> users = Arrays.asList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("coogie", "giecoo", "김효건 씨", "bal@gmail.com"));
        map.put("users", users);
        Template template = handlebars.compileInline("{{#users}}" +
                "{{coogie @index}} Hello!" +
                "{{/users}}");
        System.out.println(template.apply(map));
    }

    @Test
    void as() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("coogie", (context, options) -> (Integer) context + 1);

        Map<String, Object> map = new HashMap<>();
        List<User> users = Arrays.asList(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("coogie", "giecoo", "김효건 씨", "bal@gmail.com"));
        map.put("users", users);
        Template template = handlebars.compile("user/list");
        String aa = template.apply(map);
        System.out.println(aa);
    }
}
