package webserver.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.View;
import webserver.WebTestForm;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginControllerTest extends WebTestForm {

    LoginController loginController = new LoginController();

    @Test
    void 로그인_GET_요청_에러_처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/user/login");

        assertThrows(NotSupportedHttpMethodException.class, () -> loginController.service(httpRequest));
    }

    @Test
    void 로그인_실패_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/user/login");

        User user = User.Builder.anUser()
                .userId("alswns")
                .password("pwdd")
                .email("a@a")
                .name("alswns")
                .build();

        DataBase.addUser(user);
        View view = loginController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/redirect:/user/login_failed.html");
    }

    @Test
    void 로그인_성공_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/user/login");
        User user = User.Builder.anUser()
                .userId("alswns")
                .password("pwd")
                .email("a@a")
                .name("alswns")
                .build();

        DataBase.addUser(user);
        View view = loginController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/redirect:/index.html");
    }
}