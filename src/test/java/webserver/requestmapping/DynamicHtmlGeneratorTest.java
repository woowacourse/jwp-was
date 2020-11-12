package webserver.requestmapping;

import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.requestmapping.DynamicHtmlGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicHtmlGeneratorTest {

    @Test
    void generate() {
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profileHtml = DynamicHtmlGenerator.applyHandlebar("user/profile", user);

        assertThat(profileHtml).contains("javajigi@gmail.com");
        assertThat(profileHtml).contains("자바지기");
    }
}
