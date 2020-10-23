package web.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static web.server.common.IoUtil.createOutputStream;
import static web.server.common.IoUtil.createRequest;
import static web.server.common.IoUtil.readFile;

import db.DataBase;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import web.application.domain.model.User;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

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

    @DisplayName("회원정보를 요청했을 때 쿠키를 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"http_login_success.txt,true", "http_login_failed.txt,false"})
    void doPost(String fileName, String expected) throws IOException {
        HttpRequest httpRequest = createRequest(fileName);
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/out/root_page.txt"));

        UserLoginController userLoginController = new UserLoginController();
        userLoginController.service(httpRequest, httpResponse);

        String actual = readFile("/out/root_page.txt");

        assertThat(actual).contains("Set-Cookie: logined=" + expected);
    }
}
