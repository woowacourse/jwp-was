package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    private static Map<String, String> userParameters;

    @BeforeEach
    void setUp() {
        userParameters = new HashMap<>();

        userParameters.put("userId", "javajigi");
        userParameters.put("password", "password");
        userParameters.put("name", "자바지기");
        userParameters.put("email", "javajigi@gmail.com");
    }

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        String profilePage = template.apply(null);
        log.debug("ProfilePage : {}", profilePage);
    }
}
