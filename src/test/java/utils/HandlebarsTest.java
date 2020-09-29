package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.domain.User;
import model.dto.UsersDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("plusOne", (Helper<Integer>) (context, options) -> context + 1);

        Template template = handlebars.compile("user/list");

        List<User> users = new ArrayList<>();
        users.add(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"));
        users.add(new User("ordincode", "1234", "학성", "email"));
        UsersDto usersDto = new UsersDto(users);
        String profilePage = template.apply(usersDto);
        log.debug("ProfilePage : {}", profilePage);
    }
}
