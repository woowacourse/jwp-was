package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    @DisplayName("템플릿페이지에 사용자 정의 객체 넣어보기")
    void handlebars_applyUserDefinedClassInstance() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);

        assertThat(profilePage.contains(user.getName())).isTrue();
        assertThat(profilePage.contains(user.getEmail())).isTrue();
        log.debug(profilePage);
    }

    @Test
    @DisplayName("이터레이트가 가능한 타입")
    void handlebars_applyIterable() throws Exception {
        Handlebars handlebars = new Handlebars();
        String templateWithIterable = "hello\n" +
                "{{#.}}\n" +
                "        {{userId}}\n" +
                "        {{name}}\n" +
                "{{/.}}";

        Template template = handlebars.compileInline(templateWithIterable);

        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
                new User("eunsukko", "password", "미스터코", "eunsuk.ko128@gmail.com")
        );
        String profilePage = template.apply(users);

        log.debug(profilePage);
        for (User user : users) {
            assertThat(profilePage.contains(user.getUserId())).isTrue();
            assertThat(profilePage.contains(user.getName())).isTrue();
        }
    }
}
