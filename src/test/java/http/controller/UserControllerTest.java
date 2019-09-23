package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserControllerTest {
    private Controller controller = new UserController(RequestMapping.POST("/user/create"));

    @Test
    void 유저컨트롤러_처리_결과가_리다이렉트인지() {
        ServletRequest request = ServletRequest.builder()
                .method(HttpMethod.POST)
                .uri("/user/create")
                .protocol("HTTP/1.1")
                .params(new HashMap<String, String>() {{
                    put("userId", "userId");
                    put("password", "password");
                    put("name", "name");
                    put("email", "email");
                }})
                .build();
        ServletResponse response = new ServletResponse();

        assertThat(controller.canHandle(request)).isTrue();

        controller.handle(request, response);

        assertThat(response.getUri()).isNull();
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}