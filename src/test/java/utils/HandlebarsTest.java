package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi22", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @DisplayName("사용자 목록을 동적 조회")
    @Test
    void listTest() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        UserDto user1 = new UserDto("javajigi", "name", "javajigi@gmail.com");
        UserDto user2 = new UserDto("javajigi2", "name", "javajigi@gmail.com");

        List<UserDto> users = Arrays.asList(user1, user2);

        Map<String,Object> model = new HashMap<>();
        model.put("users",users);

        String listPage = template.apply(model);

        assertThat(listPage).contains(user1.getUserId());
        assertThat(listPage).contains(user1.getName());
        assertThat(listPage).contains(user2.getUserId());
        assertThat(listPage).contains(user2.getName());
    }
}
