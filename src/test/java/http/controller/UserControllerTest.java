package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserControllerTest {
    private Controller controller = new UserController(RequestMapping.POST("/user/create"));
    private ServletRequest request;
    private ServletResponse response;

    @BeforeEach
    void setUp() {
        request = ServletRequest.builder()
                .requestLine(HttpMethod.POST, "/user/create", "HTTP/1.1")
                .params(new HashMap<String, String>() {{
                    put("userId", "userId");
                    put("password", "password");
                    put("name", "name");
                    put("email", "email");
                }})
                .build();
        response = new ServletResponse();
    }

    @Test
    void 유저컨트롤러_처리_결과가_리다이렉트인지() {
        assertThat(controller.canHandle(request)).isTrue();

        controller.handle(request, response);

        assertThat(response.hasResource()).isFalse();
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void 유저컨트롤러_처리_후_세션_처리() {
        controller.handle(request, response);

        assertThat(response.getHeader("Set-Cookie")).isNotNull();
    }
}