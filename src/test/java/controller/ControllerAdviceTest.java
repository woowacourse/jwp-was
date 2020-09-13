package controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_INDEX_HTML;
import static webserver.FileNameExtension.API;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.childOfException;
import util.childOfIllegalArgumentException;
import webserver.HttpMethod;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

class ControllerAdviceTest {

    private ControllerAdvice controllerAdvice = new ControllerAdvice();
    private HttpRequest httpRequest = new HttpRequest(
        HttpMethod.GET.name(), URL_INDEX_HTML, new HashMap<>(), PROTOCOL, new HashMap<>(), API
    );

    @DisplayName("IllegalArgumentException일 때, BadRequst 반환")
    @Test
    void handleException_IllegalArgumentException_BadRequest() {
        HttpResponse httpResponse
            = controllerAdvice.handleException(httpRequest, new IllegalArgumentException());

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        assertThat(httpResponse.getBody())
            .contains(HttpStatusCode.BAD_REQUEST.getMessage().getBytes(StandardCharsets.UTF_8));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("IllegalArgumentException를 상속받은 Exception일 때, BadRequst 반환")
    @Test
    void handleException_ExtendsIllegalArgumentException_BadRequest() {
        HttpResponse httpResponse
            = controllerAdvice.handleException(httpRequest, new childOfIllegalArgumentException());

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        byte[] expectedBody = HttpStatusCode.BAD_REQUEST.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("NullPointerException일 때, BadRequst 반환")
    @Test
    void handleException_NullPointerException_BadRequest() {
        HttpResponse httpResponse
            = controllerAdvice.handleException(httpRequest, new NullPointerException());

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        assertThat(httpResponse.getBody())
            .contains(HttpStatusCode.BAD_REQUEST.getMessage().getBytes(StandardCharsets.UTF_8));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("Exception을 상속받은 Exception일 때, Internal Server Error반환")
    @Test
    void handleException_ExtendsException_BadRequest() {
        HttpResponse httpResponse
            = controllerAdvice.handleException(httpRequest, new childOfException());

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode())
            .isEqualTo(HttpStatusCode.INTERNAL_SERVER_ERROR);
        byte[] expectedBody = HttpStatusCode.INTERNAL_SERVER_ERROR.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }
}