package webserver.login;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {
    private static final String ID = "myId";
    private static final String PASSWORD = "myPassword";
    private static final LoginController loginController = new LoginController();

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인 성공")
    @Test
    void loginSuccessTest() {
        DataBase.addUser(new User(ID, PASSWORD, "myName", "my@email.com"));

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", ID);
        userInfo.put("password", PASSWORD);

        HttpRequest httpRequest = new HttpRequest(null, userInfo);
        HttpResponse httpResponse = new HttpResponse(null);

        loginController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaders().get("Set-Cookie")).isEqualTo("logined=true; Path=/");
        assertThat(httpResponse.getHeaders().get("Location")).isEqualTo("http://localhost:8080/index.html");
    }

    @DisplayName("로그인 실패")
    @Test
    void loginFailTest() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", ID);
        userInfo.put("password", PASSWORD);

        HttpRequest httpRequest = new HttpRequest(null, userInfo);
        HttpResponse httpResponse = new HttpResponse(null);

        loginController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaders().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
        assertThat(httpResponse.getHeaders().get("Location")).isEqualTo("http://localhost:8080/user/login_failed.html");
    }
}
