package controller;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponseEntity;
import http.response.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private UserController userController = new UserController();
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    void user_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);

        HttpResponseEntity responseEntity = userController.doPost(httpRequest);
        assertThat(responseEntity.getViewTemplatePath()).isEqualTo("index.html");
        assertThat(responseEntity.getStatus()).isEqualTo(HttpStatus.FOUND);
    }
}