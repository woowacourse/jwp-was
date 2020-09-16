package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.User;

class UserExtractorTest {
    private UserExtractor userExtractor;

    @BeforeEach
    void setUp() {
        userExtractor = new UserExtractor();
    }

    @Test
    void extract() {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Object o = userExtractor.extract(body);
        assertAll(
            () -> assertThat(((User)o).getUserId()).isEqualTo("javajigi")
        );
    }
}
