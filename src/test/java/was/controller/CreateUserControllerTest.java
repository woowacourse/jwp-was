package was.controller;

import db.DataBase;
import org.junit.jupiter.api.Test;
import was.controller.common.ControllerTemplate;
import was.model.User;
import webserver.http.request.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserControllerTest extends ControllerTemplate {

    @Test
    void 사용자_생성() {
        signUp();

        assertEquals(
                new User("ddu0422", "1234", "mir", "ddu0422@naver.com"),
                DataBase.findUserById("ddu0422")
        );
    }

    @Test
    void 지원하지_않는_메서드_요청() {
        allowedNotMethod(HttpMethod.GET, CreateUserController.getInstance());
    }
}