package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.*;

import static controller.AbstractControllerTest.TEST_DIRECTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @DisplayName("POST로 LoginController에 요청시, 로그인이 수행되고 302 FOUND가 반환된다.")
    @Test
    void serviceLoginControllerPostTest() {
        User user = new User("javajigi", "1234", "박재성", "pobi@gmail.com");
        DataBase.addUser(user);
        Controller controller = new LoginController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_LOGIN.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            String result = outputStream.toString();
            assertAll(
                    () -> assertThat(result).contains("HTTP/1.1 302 FOUND"),
                    () -> assertThat(result).contains("Set-Cookie: logined=true; Path=/")
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @DisplayName("예외 테스트: 로그인 도중 회원이 존재하지 않을 경우, 실패 페이지가 302 FOUND로 반환된다.")
    @Test
    void serviceLoginControllerNotFoundExceptionTest() {
        Controller controller = new LoginController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_LOGIN.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            String result = outputStream.toString();
            assertAll(
                    () -> assertThat(result).contains("HTTP/1.1 302 FOUND"),
                    () -> assertThat(result).contains("Location: /user/login_failed.html "),
                    () -> assertThat(result).contains("Set-Cookie: logined=false")
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @DisplayName("예외 테스트: 로그인 도중 비밀번호가 맞지 않는 경우, 실패 페이지가 302 FOUND로 반환된다.")
    @Test
    void serviceLoginControllerPasswordExceptionTest() {
        User user = new User("javajigi", "OH-MY-GIRL", "박재성", "pobi@gmail.com");
        DataBase.addUser(user);
        Controller controller = new LoginController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_LOGIN.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            String result = outputStream.toString();
            assertAll(
                    () -> assertThat(result).contains("HTTP/1.1 302 FOUND"),
                    () -> assertThat(result).contains("Location: /user/login_failed.html "),
                    () -> assertThat(result).contains("Set-Cookie: logined=false")
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
