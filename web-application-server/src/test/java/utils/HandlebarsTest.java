package utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void applyByMap() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("test");

        Map<String, String> map = new HashMap<>();
        map.put("name", "자바지기");
        map.put("email", "javajigi@gmail.com");

        String profilePage = template.apply(map);
        log.debug("ProfilePage : {}", profilePage);
    }
}
