package webserver.requestmapping;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicHtmlGeneratorTest {

    @Test
    void generateProfile() {
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profileHtml = DynamicHtmlGenerator.applyHandlebar("user/profile", user);

        assertThat(profileHtml).contains("javajigi@gmail.com");
        assertThat(profileHtml).contains("자바지기");
    }

    @Test
    void generateList() {
        List<User> users = Arrays.asList(
            new User("javajigi", "password", "자바지기", "javajigi@gmail.com"),
            new User("yuan", "password", "유안", "yuan@gmail.com"),
            new User("bum", "password", "범블비", "bum@gmail.com")
        );

        String listHtml = DynamicHtmlGenerator.applyHandlebar("user/list", users);
        assertThat(listHtml).contains("유안");
        assertThat(listHtml).contains("범블비");
        assertThat(listHtml).contains("자바지기");
    }
}
