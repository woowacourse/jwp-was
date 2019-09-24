package controller;

import org.junit.jupiter.api.Test;
import webserver.HttpResponse;
import webserver.HttpStatus;
import webserver.RequestDispatcher;
import webserver.RequestParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SingUpControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @Test
    void create() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostSignUp.txt"));
        HttpResponse res = new HttpResponse();
        new SignUpController().service(RequestParser.parse(in), res);

        assertThat(res.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/index.html");
    }
}
