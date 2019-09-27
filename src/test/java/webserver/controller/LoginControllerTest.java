package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.WebTestForm;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest extends WebTestForm {

    @Test
    void 로그인_실패_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequest("/user/login");
        HttpResponse httpResponse = new HttpResponse();

        User user = User.Builder.anUser()
                .userId("alswns")
                .password("pwdd")
                .email("a@a")
                .name("alswns")
                .build();

        DataBase.addUser(user);
        String view = LoginController.getInstance().service(httpRequest, httpResponse);
        assertThat(view).isEqualTo("/redirect:/user/login_failed.html");
    }

    @Test
    void 로그인_성공_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/user/login");
        HttpResponse httpResponse = new HttpResponse();
        User user = User.Builder.anUser()
                .userId("alswns")
                .password("pwd")
                .email("a@a")
                .name("alswns")
                .build();

        DataBase.addUser(user);
        String view = LoginController.getInstance().service(httpRequest, httpResponse);
        assertThat(view).isEqualTo("/redirect:/index.html");
    }
}