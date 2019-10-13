package controller;

import controller.exception.HttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.common.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MainControllerTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: text/html";

    private static final String POST_REQUEST_MESSAGE =
            "POST /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    @DisplayName("/index.html GET 요청")
    @Test
    void doGet() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new MainController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.setStatusLine(httpRequest, HttpStatus.OK);
        httpResponseToCompare.setHeader("Content-Type", "text/html;charset=utf-8");
        httpResponseToCompare.setHeader("Content-Length", "6902");
        httpResponseToCompare.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/index.html"));

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }

    @DisplayName("/index.html POST 요청")
    @Test
    void doPost() {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new MainController();
        assertThatExceptionOfType(HttpRequestException.class)
                .isThrownBy(() -> controller.service(httpRequest, httpResponse))
                .withMessage("405 Method Not Allowed");
    }
}
