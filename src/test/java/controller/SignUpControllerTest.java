package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestDispatcher;
import webserver.RequestParser;
import webserver.Response;
import webserver.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @Test
    @DisplayName("회원가입 성공 통합테스트")
    void create() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostSignUp.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/index.html");
    }
}