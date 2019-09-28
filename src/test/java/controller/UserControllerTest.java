package controller;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static http.response.HttpStatus.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private UserController userController = new UserController();

    @Test
    void user_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest request = HttpRequestFactory.getHttpRequest(in);
        HttpResponse response = HttpResponse.of(request.getVersion());

        userController.doPost(request, response);

        assertThat(response.getMessageHeader())
                .isEqualTo(request.getVersion() + " " + FOUND.getMessage() + "\r\n"
                        + "Location: /index.html\r\n");
    }
}