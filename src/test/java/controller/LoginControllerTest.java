package controller;

import controller.exception.NotFoundUserIdException;
import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import model.User;
import model.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.IOException;

import static http.HttpRequestTest.GET_REQUEST;
import static http.HttpRequestTest.LOGIN_REQUEST;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.IOUtils.convertStringToInputStream;

public class LoginControllerTest {
    private LoginController loginController = new LoginController();

    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    @Test
    void 유저_로그인_성공() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(LOGIN_REQUEST, ID, PASSWORD)));
        HttpResponse response = new HttpResponse();

        View view = loginController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("index.html");
    }

    @Test
    void 없는_유저_아이디로_로그인() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(LOGIN_REQUEST, "ABC", PASSWORD)));
        HttpResponse response = new HttpResponse();

        assertThrows(NotFoundUserIdException.class, () -> loginController.service(request, response));
    }

    @Test
    void 다른_비밀번호로_로그인() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(LOGIN_REQUEST, ID, "PASS")));
        HttpResponse response = new HttpResponse();

        assertThrows(InvalidPasswordException.class, () -> loginController.service(request, response));
    }

    @Test
    void GET_메소드_에러() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(GET_REQUEST));
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> loginController.service(request, response));
    }


}
