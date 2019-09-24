package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest extends BaseControllerTest {
    private Controller controller = new UserListController(RequestMapping.GET("/user/list"));

    @Test
    void 로그인후_요청시_모델이_포함되는지() {
        ServletRequest request = getDefaultRequest(HttpMethod.GET, "/user/list")
                .cookie(new HashMap<String, String>() {{
                    put("logined", "true");
                }}).build();
        ServletResponse response = getDefaultResponse();

        controller.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasModel()).isTrue();
        assertThat(response.getModel()).isNotNull();
    }

    @Test
    void 로그인없이_요청후_리다이렉트되는지() {
        ServletRequest request = getDefaultRequest(HttpMethod.GET, "/user/list").build();
        ServletResponse response = getDefaultResponse();

        controller.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.hasModel()).isFalse();
    }
}