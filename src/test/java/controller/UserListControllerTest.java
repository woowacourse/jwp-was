package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.session.HttpSession;
import web.session.SessionStore;
import web.session.WebSession;

import java.io.*;

import static controller.AbstractControllerTest.TEST_DIRECTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserListControllerTest.class);

    @DisplayName("GET으로 '/user/list'에 접근할 시, 로그인이 되어있다면 목록을 표시한다.")
    @Test
    void doGetWithLoginTest() {
        User kafka = new User("kafka123", "password", "kafka", "kafka@naver.com");
        User pobi = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        DataBase.addUser(kafka);
        DataBase.addUser(pobi);
        HttpSession session = new WebSession();
        session.setAttribute("id", "javajigi@gmail.com");
        SessionStore.addSession("1", session);
        Controller controller = new UserListController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_USER_LIST_LOGIN.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            String result = outputStream.toString();
            assertAll(
                    () -> assertThat(result).contains("HTTP/1.1 200 OK"),
                    () -> assertThat(result).contains(kafka.getName()),
                    () -> assertThat(result).contains(kafka.getEmail()),
                    () -> assertThat(result).contains(kafka.getUserId()),
                    () -> assertThat(result).contains(pobi.getName()),
                    () -> assertThat(result).contains(pobi.getEmail()),
                    () -> assertThat(result).contains(pobi.getUserId())
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @DisplayName("GET으로 '/user/list'에 접근할 시, 로그인이 되어있지 않다면 로그인 페이지로 이동한다.")
    @Test
    void doGetWithLogoutTest() {
        Controller controller = new UserListController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_USER_LIST_LOGOUT.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            String result = outputStream.toString();
            assertAll(
                    () -> assertThat(result).contains("HTTP/1.1 302 FOUND"),
                    () -> assertThat(result).contains("Location: /user/login.html ")
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
