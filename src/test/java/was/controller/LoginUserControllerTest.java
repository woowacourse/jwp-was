package was.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import was.controller.common.ControllerTemplate;
import webserver.http.request.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class
LoginUserControllerTest extends ControllerTemplate {

    @BeforeEach
    @DisplayName("로그인 전 회원가입하기")
    void setUp() {
        signUp();
    }

    @Test
    void 로그인_성공() {
        signIn();

        assertEquals("/index.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=true; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 로그인_실패() {
        singInFailed();

        assertEquals("/user/login_failed.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=false; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 로그인_성공_후_다시_로그인_요청_시_실패했을_경우() {
        signIn();
        singInFailed();

        assertEquals("/user/login_failed.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=false; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 지원하지_않는_메서드_요청() {
        allowedNotMethod(HttpMethod.GET, LoginUserController.getInstance());
    }
}
