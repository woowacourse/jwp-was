package webserver.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.UserDataBase;
import webserver.HttpRequestFixture;
import webserver.TemplateFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

class UserListControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new UserListController();
    }

    @DisplayName("로그인한 유저일 경우 회원 목록을 보여준다.")
    @Test
    void get_shouldRedirectUserListPage_whenLogined() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestByLoginedUser();

        HttpResponse httpResponse = controller.service(httpRequest);

        HttpResponse expected = HttpResponse.ok()
            .body(TemplateFactory.of("user/list", UserDataBase.findAll()))
            .build();
        assertAll(() -> assertThat(httpResponse.getStatusLine().getValue()).isEqualTo(expected.getStatusLine().getValue()),
            () -> assertThat(httpResponse.getHeader()).isEqualTo(expected.getHeader()),
            () -> assertThat(httpResponse.getBody()).isEqualTo(expected.getBody())
        );
    }

    @DisplayName("로그인하지 않은 유저일 경우 로그인 화면을 보여준다.")
    @Test
    void get_shouldRedirectLoginPage_whenNotLogined() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestByNotLoginedUser();

        HttpResponse httpResponse = controller.service(httpRequest);

        HttpResponse expected = HttpResponse.ok().bodyByPath("./templates/user/login.html").build();
        assertAll(() -> assertThat(httpResponse.getStatusLine().getValue()).isEqualTo(expected.getStatusLine().getValue()),
            () -> assertThat(httpResponse.getHeader()).isEqualTo(expected.getHeader()),
            () -> assertThat(httpResponse.getBody()).isEqualTo(expected.getBody())
        );
    }
}