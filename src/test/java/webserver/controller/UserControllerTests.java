package webserver.controller;

import exception.InvalidSignUpDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;

public class UserControllerTests extends BasicTests {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void doGet() throws IOException {
        makeRequest("Http_userGET.txt");
        HttpResponse httpResponse = userController.doGet(httpRequest);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("." + NON_STATIC_FILE_PATH + "/user/form.html").get());
    }

    @Test
    void doPost() throws IOException {
        makeRequest("Http_POST.txt");
        HttpResponse httpResponse = userController.doPost(httpRequest);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaderFields().get("Location")).isEqualTo("/index.html");
    }

    @Test
    void invalid_signup_data_doPost() throws IOException {
        makeRequest("Http_invalidSignUp.txt");
        assertThrows(InvalidSignUpDataException.class, () -> userController.doPost(httpRequest));
    }
}
