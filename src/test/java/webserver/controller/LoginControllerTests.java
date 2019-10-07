package webserver.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.response.HttpResponse;
import webserver.controller.response.HttpStatus;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;
import static webserver.controller.LoginController.LOGIN_SUCCESS_INDEX;

public class LoginControllerTests extends BasicTests {
    private LoginController loginController;
    private UserController userController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
        userController = new UserController();
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


    @Test
    void login_fail_redirect() throws IOException {
        makeRequest("Http_POST.txt");
        userController.doPost(httpRequest);

        makeRequest("Http_loginFail.txt");
        String redirectUrl = "/user/login_failed.html";

        HttpResponse httpResponse = loginController.doPost(httpRequest);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        Assertions.assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void login_sucess_redirect() throws IOException {
        makeRequest("Http_POST.txt");
        userController.doPost(httpRequest);

        makeRequest("Http_loginPOST.txt");
        String redirectUrl = "/index.html";
        HttpResponse httpResponse = loginController.doPost(httpRequest);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        Assertions.assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        Assertions.assertThat(httpResponse.getCookieFields().get(0)).isEqualTo("logined=true");
        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}
