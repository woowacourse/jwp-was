package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("허용하지 않는 메서드")
    void doPost() throws IOException {
        String input = "POST /user/create HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 45\r\n" +
                "\r\n" +
                "userId=zz&password=zz&name=zz&email=zz@bzz.com";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        Controller controller = new Controller();
        controller.service(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 405 Method Not Allowed\r\n");
    }

    @Test
    @DisplayName("허용하지 않는 메서드")
    void doGet() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        Controller controller = new Controller();
        controller.service(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 405 Method Not Allowed\r\n");
    }
}