package controller;

import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.IOException;

import static http.HttpRequestTest.*;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.IOUtils.convertStringToInputStream;

public class UserListControllerTest {
    private UserListController userListController = new UserListController();

    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    @Test
    void 유저_로그인시_유저_목록_접근() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(USER_LIST_REQUEST, TRUE)));
        HttpResponse response = new HttpResponse();

        View view = userListController.service(request, response);

        assertThat(view.isRedirectView()).isFalse();
        assertThat(view.getViewName()).isEqualTo("user/list.html");
    }

    @Test
    void 유저_비로그인시_유저_목록_접근() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(USER_LIST_REQUEST, FALSE)));
        HttpResponse response = new HttpResponse();

        View view = userListController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("user/login.html");
    }

    @Test
    void POST_메소드_에러() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(POST_REQUEST));
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> userListController.service(request, response));
    }
}