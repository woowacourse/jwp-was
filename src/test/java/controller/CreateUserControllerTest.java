package controller;

import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpResponseStatus;
import org.junit.jupiter.api.Test;
import webserver.DispatcherServlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserControllerTest {

    @Test
    void POST_요청으로_유저_회원가입_성공() throws IOException, URISyntaxException {
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

        assertEquals(httpRequest.getQueryParameter("userId"), "codemcd");
        assertEquals(httpRequest.getQueryParameter("password"), "1234");
        assertEquals(httpRequest.getQueryParameter("name"), "park");
        assertEquals(httpRequest.getQueryParameter("email"), "park@naver.com");

        DispatcherServlet.doDispatch(httpRequest, httpResponse);

        assertEquals(httpResponse.getStatus(), HttpResponseStatus.FOUND);
        assertEquals(httpResponse.getHeaderAttribute("Location"), "/index.html");
    }
}
