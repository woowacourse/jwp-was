package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;

class RequestUtilsTest {
    @Test
    void extractFileName() {
        String fileName = RequestUtils.extractUrl("GET /index.html HTTP/1.1");
        assertThat(fileName).isEqualTo("/index.html");
    }

    @Test
    void extractUser() {
        User user = RequestUtils.extractUser(
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertAll(
            () -> assertThat(user).isNotNull(),
            () -> assertThat(user.getUserId()).isEqualTo("javajigi")
        );
    }
}
