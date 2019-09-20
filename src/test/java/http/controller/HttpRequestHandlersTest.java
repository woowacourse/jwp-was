package http.controller;

import http.model.HttpMethod;
import http.model.HttpRequest;
import http.supoort.HttpRequestParser;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHandlersTest {
    private HttpRequestHandlers handlers = new HttpRequestHandlers();
    private HttpRequestHandler fileResourceRequestHandler;
    private HttpRequestHandler modelRequestHandler;

    @BeforeEach
    void setUp() {
        fileResourceRequestHandler = new FileResourceRequestHandler();
        modelRequestHandler = new UserRequestHandler();
        handlers.addHandler(new RequestMapping(HttpMethod.GET, "/*"), fileResourceRequestHandler);
        handlers.addHandler(new RequestMapping(HttpMethod.GET, "/user/create"), modelRequestHandler);
    }

    @Test
    void 모델핸들러_선택() {
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
}