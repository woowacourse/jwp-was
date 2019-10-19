package controller;

import controller.exception.HttpRequestException;
import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static webserver.support.ConStants.HEADER_FIELD_LOCATION;

public class SignUpControllerTest {
    private static final String POST_REQUEST_MESSAGE =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 93\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @DisplayName("/user/create GET 요청")
    @Test
    void doGet() {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new StyleSheetController();
        assertThatExceptionOfType(HttpRequestException.class)
                .isThrownBy(() -> controller.service(httpRequest, httpResponse))
                .withMessage("405 Method Not Allowed");
    }

    @DisplayName("/user/create POST 요청")
    @Test
    void doPost() throws IOException {
        DataBase.removeAll();

        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new SignUpController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.setStatusLine(httpRequest, HttpStatus.FOUND);
        httpResponseToCompare.setHeader(HEADER_FIELD_LOCATION, "http://localhost:8080/index.html");

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }
}
