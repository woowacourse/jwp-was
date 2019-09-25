package was.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import was.controller.common.ControllerTemplate;
import webserver.http.request.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginUserControllerTest extends ControllerTemplate {

    @BeforeEach
    @DisplayName("로그인 전 회원가입하기")
    public void setUp() throws Exception {
        super.setUp();
        signUp();
    }

    @Test
    void 로그인_성공() throws Exception {
        signIn();

        assertEquals("/index.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=true; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 로그인_실패() throws Exception {
        singInFailed();

        assertEquals("/user/login_failed.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=false; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 지원하지_않는_메서드_요청() throws Exception {
        allowedNotMethod(HttpMethod.GET, LoginUserController.getInstance());
    }
}
