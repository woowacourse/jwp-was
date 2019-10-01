package controller;

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

public class FailToSignInControllerTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /user/login_failed.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: text/html";

    @DisplayName("/user/login_failed GET 요청")
    @Test
    void doGet() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new FailToSignInController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.setStatusLine(httpRequest, HttpStatus.OK);
        httpResponseToCompare.setHeader("Content-Type", "text/html;charset=utf-8");
        httpResponseToCompare.setHeader("Content-Length", String.valueOf(httpResponse.getResponseBody().getLengthOfContent()));
        httpResponseToCompare.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/user/login_failed.html"));

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }
}
