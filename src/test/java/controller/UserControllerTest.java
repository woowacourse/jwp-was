package controller;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.Http302ResponseEntity;
import http.response.HttpResponseEntity;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private UserController userController = new UserController();

    @Test
    void user_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.getHttpRequest(in);

        HttpResponseEntity responseEntity = userController.doPost(httpRequest);

        assertThat(responseEntity).isEqualTo(new Http302ResponseEntity("/index.html"));
    }
}