package http.controller;

import http.exceptions.IllegalRequestMappingException;
import http.model.request.ServletRequest;
import http.model.response.HttpStatus;
import http.model.response.ServletResponse;
import http.supoort.RequestMapping;
import http.supoort.converter.request.HttpRequestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpRequestControllersTest {
    private HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/*")));

    @BeforeEach
    void setUp() {
        handlers.addHandler(new UserCreateController(RequestMapping.POST("/user/create"), RequestMapping.GET("/user/create")));
    }

    @Test
    void 유저핸들러_선택() {
        String requestMessage = "GET /user/create?key=value HTTP/1.1";
        ServletRequest request = HttpRequestParser.parse(new ByteArrayInputStream(requestMessage.getBytes()));
        ServletResponse response = new ServletResponse(new ByteArrayOutputStream());

        handlers.doService(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getProtocols()).isNotNull();
    }

    @Test
    void 파일리소스핸들러_선택() {
        String requestMessage = "GET /index.html HTTP/1.1";
        ServletRequest request = HttpRequestParser.parse(new ByteArrayInputStream(requestMessage.getBytes()));
        ServletResponse response = new ServletResponse(new ByteArrayOutputStream());

        handlers.doService(request, response);

        assertThat(response.getHttpStatus()).isNotNull();
        assertThat(response.getView()).isEqualTo("/index.html");
    }

    @Test
    void 리퀘스트매핑_중복_등록() {
        HttpRequestControllers handlers = new HttpRequestControllers(new FileResourceController(RequestMapping.GET("/")));
        handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html")));
        assertThatThrownBy(() -> handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html"))))
                .isInstanceOf(IllegalRequestMappingException.class);
    }
}