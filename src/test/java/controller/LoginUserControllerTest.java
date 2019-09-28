package controller;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.DispatcherServlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUserControllerTest {

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
    void 로그인_성공() throws IOException, URISyntaxException {
        String loginUserPostRequest = "POST /user/login HTTP/1.1\r\n" +
                "Origin: http://localhost:8080\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: localhost:8080\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Content-Length: 28\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "userId=codemcd&password=1234";

        InputStream in  = new ByteArrayInputStream(loginUserPostRequest.getBytes(UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        assertEquals(httpRequest.getQueryParameter("userId"), "codemcd");
        assertEquals(httpRequest.getQueryParameter("password"), "1234");

        DispatcherServlet.doDispatch(httpRequest, httpResponse);

        assertEquals(httpResponse.getStatus(), HttpResponseStatus.FOUND);
        assertEquals(httpResponse.getHeaderAttribute("Location"), "/index.html");
    }

    @Test
    void 로그인_실패() throws IOException, URISyntaxException {
        String loginUserPostRequest = "POST /user/login HTTP/1.1\r\n" +
                "Origin: http://localhost:8080\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\r\n" +
                "Connection: keep-alive\r\n" +
                "Host: localhost:8080\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Content-Length: 25\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "userId=park&password=1234";

        InputStream in  = new ByteArrayInputStream(loginUserPostRequest.getBytes(UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        assertEquals(httpRequest.getQueryParameter("userId"), "park");
        assertEquals(httpRequest.getQueryParameter("password"), "1234");

        DispatcherServlet.doDispatch(httpRequest, httpResponse);

        assertEquals(httpResponse.getStatus(), HttpResponseStatus.FOUND);
        assertEquals(httpResponse.getHeaderAttribute("Location"), "/user/login_failed.html");
    }
}
