package webserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;
import static webserver.controller.LoginController.LOGIN_SUCCESS_INDEX;

public class LoginControllerTests extends BasicControllerTests {
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @Test
    void doGet() throws IOException {
        makeRequest("Http_loginGET.txt");
        HttpResponse httpResponse = loginController.doGet(httpRequest);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("." + NON_STATIC_FILE_PATH + "/user/login.html").get());
    }

    @Test
    void doPost() throws IOException {
        makeRequest("Http_POST.txt");
        UserController userController = new UserController();
        userController.doPost(httpRequest);

        makeRequest("Http_loginPOST.txt");
        HttpResponse httpResponse = loginController.doPost(httpRequest);
        assertThat(httpResponse.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaderFields().get("Location")).isEqualTo(LOGIN_SUCCESS_INDEX);
    }
}
