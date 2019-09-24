package http.controller;

import db.DataBase;
import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest extends BaseControllerTest {
    private Controller controller;
    private ServletRequest request;
    private ServletResponse response;

    @BeforeEach
    void setUp() {
        request = getDefaultRequest(HttpMethod.POST, "/user/login")
                .params(new HashMap<String, String>() {{
                    put("userId", "userId");
                    put("password", "password");
                }}).build();
        response = getDefaultResponse();
        controller = new LoginController(RequestMapping.POST("/user/login"));
    }

    @Test
    void 로그인_실패() {
        controller.handle(request, response);

        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=false");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void 로그인_성공() {
        DataBase.addUser(new User("userId", "password", "andole", "andole@andole.com"));

        controller.handle(request, response);

        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);

        DataBase.deleteById("userId");
    }
}