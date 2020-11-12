package controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import http.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import model.User;
import utils.AbstractHttpRequestGenerator;

class LoginControllerTest extends AbstractHttpRequestGenerator {

    private Controller controller = new LoginController(HttpRequestMapping.POST("/user/login"));
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() throws IOException {
        httpRequest = AbstractHttpRequestGenerator.createHttpPostRequest("POST_LoginRequest");
        httpResponse = HttpResponse.from(new ByteArrayOutputStream());
    }

    @DisplayName("회원 가입 안 한 경우 - 로그인 실패")
    @Test
    void login_Fail() {
        controller.handle(httpRequest, httpResponse);

        assertAll(
            () -> assertThat(httpResponse.getHeader("Set-Cookie")).isEqualTo("logined=false"),
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND)
        );
    }

    @DisplayName("회원가입 한 경우 - 로그인 성공")
    @Test
    void login_Success() {

        DataBase.addUser(new User("sonypark", "sony123", "sony", "sony@sony.com"));

        controller.handle(httpRequest, httpResponse);

        Assertions.assertAll(
            () -> assertThat(httpResponse.getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/"),
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND)
        );

        DataBase.deleteById("sonypark");
    }
}
