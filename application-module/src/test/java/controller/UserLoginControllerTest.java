package controller;

import static common.IoUtil.createOutputStream;
import static common.IoUtil.createRequest;
import static common.IoUtil.readFile;
import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import domain.model.User;
import domain.request.HttpRequest;
import domain.response.HttpResponse;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    @CsvSource(value = {"http_login_success.txt", "http_login_failed.txt"})
    void doPost(String fileName) throws IOException {
        HttpRequest httpRequest = createRequest(fileName);
        HttpResponse httpResponse = new HttpResponse(createOutputStream("/out/root_page.txt"));

        UserLoginController userLoginController = UserLoginController.getInstance();
        userLoginController.service(httpRequest, httpResponse);

        String actual = readFile("/out/root_page.txt");

//        assertThat(actual).contains("Set-Cookie: logined=" + expected);
        assertThat(actual).contains("Set-Cookie:");
    }
}
