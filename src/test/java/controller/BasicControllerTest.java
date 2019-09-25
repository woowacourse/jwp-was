package controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicControllerTest {

    @Test
    void service_지원하지_않는_메소드() throws IOException{
        String requestWithBody = "PUT /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = IOUtils.toInputStream(requestWithBody, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
        HttpResponse httpResponse = new HttpResponse();

        UserController userController = new UserController();
        assertThrows(NotSupportedHttpMethodException.class, () -> userController.service(httpRequest, httpResponse));

    }
}