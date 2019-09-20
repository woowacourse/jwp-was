package http.controller;

import http.model.HttpRequest;
import http.supoort.HttpRequestParser;
import http.supoort.IllegalRequestMappingException;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestHandlersTest {
    private HttpRequestHandlers handlers = new HttpRequestHandlers(new FileResourceController(RequestMapping.GET("/*")));

    @BeforeEach
    void setUp() {
        handlers.addHandler(new UserController(RequestMapping.POST("/user/create"), RequestMapping.GET("/user/create")));
    }

    @Test
    void 유저핸들러_선택() {
        String request = "GET /user/create?key=value HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getModel()).isNotNull();
    }

    @Test
    void 파일리소스핸들러_선택() {
        String request = "GET /index.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getModel()).isNull();
    }

    @Test
    void 회원가입페이지_서비스() {
        String request = "GET /user/form.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getViewLocation()).isEqualTo("/user/form.html");
    }

    @Test
    void 리퀘스트매핑_중복_등록() {
        HttpRequestHandlers handlers = new HttpRequestHandlers(new FileResourceController(RequestMapping.GET("/")));
        handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html")));
        assertThatThrownBy(() -> handlers.addHandler(new FileResourceController(RequestMapping.GET("/index.html"))))
                .isInstanceOf(IllegalRequestMappingException.class);

    }
}