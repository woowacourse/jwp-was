package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);


        Template template = handlebars.compile("user/profile");
        Map<String, Object> models = new HashMap<>();
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User user2 = new User("kangmin", "password", "김강민", "kangmin@gmail.com");
        models.put("users", Arrays.asList(user,user2));
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }
}
