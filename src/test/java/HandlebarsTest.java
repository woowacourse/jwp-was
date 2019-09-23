import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        Template template = handlebars.compile("/user/list");

        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com")
        );

        Map<String, Object> map = new HashMap<>();
        map.put("users", users);

        String profilePage = template.apply(map);
        log.debug("ProfilePage : {}", profilePage);
    }


}
