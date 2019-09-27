package webserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class UserControllerTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /user/form.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private static final String POST_REQUEST_MESSAGE =
            "POST /user/form.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    @DisplayName("/user/form.html GET 요청")
    @Test
    void doGet() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new UserController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.addStatusLine(httpRequest, "200", "OK");
        httpResponseToCompare.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponseToCompare.addHeader("Content-Length", "5168");
        httpResponseToCompare.addBody(FileIoUtils.loadFileFromClasspath("./templates" + "/user/form.html"));

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }

    @DisplayName("/user/form.html POST 요청")
    @Test
    void doPost() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new UserController();
        assertThatExceptionOfType(MethodNotAllowedException.class)
                .isThrownBy(() -> controller.service(httpRequest, httpResponse))
                .withMessage("fail to match method.");
    }
}
