package webserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.HttpStatus;
import webserver.controller.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StyleSheetControllerTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET ./css/styles.css HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Accept: text/css,*/*;q=0.1\n" +
                    "Connection: keep-alive";

    private static final String POST_REQUEST_MESSAGE =
            "POST ./css/styles.css HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Accept: text/css,*/*;q=0.1\n" +
                    "Connection: keep-alive";

    @DisplayName("css타입의 파일을 GET 요청")
    @Test
    void doGet() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new StyleSheetController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.addStatusLine(httpRequest, HttpStatus.OK);
        httpResponseToCompare.addHeader("Content-Type", "text/css");
        httpResponseToCompare.addHeader("Content-Length", String.valueOf(httpResponse.getResponseBody().getLengthOfContent()));
        httpResponseToCompare.addBody(FileIoUtils.loadFileFromClasspath("./static" + "/css/styles.css"));

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }

    @DisplayName("css타입의 파일을 POST 요청")
    @Test
    void doPost() {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new StyleSheetController();
        assertThatExceptionOfType(MethodNotAllowedException.class)
                .isThrownBy(() -> controller.service(httpRequest, httpResponse))
                .withMessage("405 Method Not Allowed");
    }
}
