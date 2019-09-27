package was.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import was.controller.common.ControllerTemplate;
import webserver.http.request.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUserControllerTest extends ControllerTemplate {

    @BeforeEach
    @DisplayName("회원가입")
    void setUp() {
        signUp();
    }

    @Test
    void 로그인_성공_후_회원_리스트_페이지_이동_성공() {
        signIn();
        movePage("/user/list", httpRequest.getHttpHeader().get("Cookie"));

        Controller controller = ListUserController.getInstance();
        controller.service(httpRequest, httpResponse);

        assertEquals("logined=true; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    @Test
    void 로그인_실패_후_회원_리스트_페이지_이동_실패() {
        singInFailed();
        movePage("/user/list", httpRequest.getHttpHeader().get("Cookie"));

        Controller controller = ListUserController.getInstance();
        controller.service(httpRequest, httpResponse);

        assertEquals("/user/login.html", httpResponse.getHttpHeader().get("Location"));
    }

    @Test
    void 지원하지_않는_메서드_요청() {
        allowedNotMethod(HttpMethod.POST, ListUserController.getInstance());
    }
}
