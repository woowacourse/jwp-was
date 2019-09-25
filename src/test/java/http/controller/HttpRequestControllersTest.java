package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpRequestControllersTest extends BaseControllerTest {
    private HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/*")));

    @BeforeEach
    void setUp() {
        handlers.addHandler(new UserCreateController(RequestMapping.POST("/user/create"), RequestMapping.GET("/user/create")));
    }

    @Test
    void 유저핸들러_선택() {
        ServletRequest request = getDefaultRequest(HttpMethod.GET, "/user/create")
                .params(new HashMap<String, String>() {{
                    put("userId", "userId");
                    put("password", "password");
                    put("name", "name");
                    put("email", "email");
                }})
                .build();
        ServletResponse response = getDefaultResponse();

        handlers.doService(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getProtocols()).isNotNull();
    }

    @Test
    void 파일리소스핸들러_선택() {
        ServletRequest request = getDefaultRequest(HttpMethod.GET, "/index.html").build();
        ServletResponse response = new ServletResponse(new ByteArrayOutputStream());

        handlers.doService(request, response);

        assertThat(response.getHttpStatus()).isNotNull();
        assertThat(response.getView()).isEqualTo("/index.html");
    }

    @Test
    void 동일_리퀘스트매핑이_이미_존재할경우() {
        HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/")));
        handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html")));
        assertThatThrownBy(() -> handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html"))))
                .isInstanceOf(IllegalRequestMappingException.class);
    }

    @Test
    void 리퀘스트매핑_URI만_중복인경우() {
        HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/")));
        handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html")));
        assertDoesNotThrow(() -> handlers.addHandler(new FileResourceController(RequestMapping.POST("/index.html"))));
    }

    @Test
    void 리퀘스트매핑_메서드만_중복인경우() {
        HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/")));
        handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html")));
        assertDoesNotThrow(() -> handlers.addHandler(new FileResourceController(RequestMapping.GET("/another.html"))));
    }

    @Test
    void name() {
    }
}