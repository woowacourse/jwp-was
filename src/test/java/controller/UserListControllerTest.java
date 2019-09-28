package controller;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.DispatcherServlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserListControllerTest {

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // 회원가입
        String userCreatePostRequest = "POST /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=codemcd&password=1234&name=park&email=park@naver.com";

        InputStream in = new ByteArrayInputStream(userCreatePostRequest.getBytes(UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        DispatcherServlet.doDispatch(httpRequest, httpResponse);
    }

    @Test
    void 로그인_후_유저_리스트_페이지_이동() throws IOException, URISyntaxException {
        String userListGetRequestAfterLogin =
                        "GET /user/list HTTP/1.1\n" +
                        "Cookie: logined=true\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
                        "Connection: keep-alive\n" +
                        "Host: localhost:8080\n";

        InputStream in = new ByteArrayInputStream(userListGetRequestAfterLogin.getBytes(UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        DispatcherServlet.doDispatch(httpRequest, httpResponse);

        assertEquals(httpResponse.getStatus(), HttpStatus.OK);
        assertThat(httpResponse.getBody()).contains("codemcd".getBytes());
        assertThat(httpResponse.getBody()).contains("park@naver.com".getBytes());
    }

    @Test
    void 로그인_없이_유저_리스트_페이지_이동_오류() throws IOException, URISyntaxException {
        String userListGetRequestAfterLogin =
                        "GET /user/list HTTP/1.1\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n" +
                        "Connection: keep-alive\n" +
                        "Host: localhost:8080\n";

        InputStream in = new ByteArrayInputStream(userListGetRequestAfterLogin.getBytes(UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        DispatcherServlet.doDispatch(httpRequest, httpResponse);

        assertEquals(httpResponse.getStatus(), HttpStatus.FOUND);
        assertEquals(httpResponse.getHeaderAttribute("Location"), "/user/login.html");
    }
}
