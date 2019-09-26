package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    @DisplayName("인덱스 페이지 이동")
    void doGet() throws IOException {
        String input = "GET / HTTP/1.1\r\n" +
                "Cache-Control: true\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        HomeController homeController = new HomeController();
        homeController.service(httpRequest, httpResponse);
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 200 OK\r\n");
        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Content-Type"), "text/html;charset=utf-8");
        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Content-Length"), "6902");
    }
}