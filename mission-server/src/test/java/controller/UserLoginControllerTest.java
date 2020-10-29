package controller;

import static org.assertj.core.api.Assertions.*;
import static test.IoUtil.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import domain.model.User;
import domain.response.HttpServletResponse;
import servlet.HttpRequest;
import servlet.HttpResponse;

class UserLoginControllerTest {

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .userId("pobi")
            .password("zzang")
            .name("jaesung")
            .email("woowahan@naver.com")
            .build();
        DataBase.addUser(user);
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteUser("pobi");
    }

    @DisplayName("로그인을 요청했을 때 세션이 연결되어있는지 확인한다.")
    @Test
    void doPost() throws IOException {
        HttpRequest httpRequest = createRequest("http_login_success.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/root_page.txt"));

        UserLoginController userLoginController = new UserLoginController();
        userLoginController.service(httpRequest, httpResponse);

        String actual = readFile("/out/root_page.txt");

        assertThat(actual).contains("Set-Cookie: JSESSIONID=");
    }
}
