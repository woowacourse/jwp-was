package util;

import com.github.jknack.handlebars.Template;
import model.domain.User;
import model.dto.UsersDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateMaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

class TemplateMakerTest {
    private static final Logger log = LoggerFactory.getLogger(TemplateMaker.class);

    @Disabled
    @DisplayName("handlebars template 테스트")
    @Test
    void name() throws IOException {
        Template template = TemplateMaker.handlebarsCompile("user/list");

        Collection<User> users = new ArrayList<>();
        users.add(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"));
        users.add(new User("ordincode", "1234", "학성", "email"));
        UsersDto usersDto = new UsersDto(users);
        String profilePage = template.apply(usersDto);
        log.debug("ProfilePage : {}", profilePage);
    }
}
