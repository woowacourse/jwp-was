package controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static util.Constants.HEADERS_EMPTY;
import static util.Constants.PARAMETERS_EMPTY;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_PATH_INDEX_HTML;
import static webserver.FileNameExtension.API;
import static webserver.HttpMethod.GET;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.childOfException;
import util.childOfIllegalArgumentException;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

class ControllerAdviceTest {

    private ControllerAdvice controllerAdvice = new ControllerAdvice();
    private HttpRequest httpRequest
        = new HttpRequest(GET, URL_PATH_INDEX_HTML, PARAMETERS_EMPTY, PROTOCOL, HEADERS_EMPTY, API);

    @DisplayName("IllegalArgumentException이 Cause일 때, BadRequst 반환")
    @Test
    void handleException_CauseOfIllegalArgumentException_BadRequest() {
        RuntimeException exception
            = makeRuntimeExceptionWithCause(new IllegalArgumentException());
        HttpResponse httpResponse
            = controllerAdvice.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        assertThat(httpResponse.getBody())
            .contains(HttpStatusCode.BAD_REQUEST.getMessage().getBytes(StandardCharsets.UTF_8));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("IllegalArgumentException를 상속받은 Exception이 Cause일 때, BadRequst 반환")
    @Test
    void handleException_CauseOfExtendsIllegalArgumentException_BadRequest() {
        RuntimeException exception
            = makeRuntimeExceptionWithCause(new childOfIllegalArgumentException());
        HttpResponse httpResponse
            = controllerAdvice.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        byte[] expectedBody = HttpStatusCode.BAD_REQUEST.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("NullPointerException이 Cause일 때, BadRequst 반환")
    @Test
    void handleException_CauseOfNullPointerException_BadRequest() {
        RuntimeException exception
            = makeRuntimeExceptionWithCause(new NullPointerException());
        HttpResponse httpResponse
            = controllerAdvice.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        assertThat(httpResponse.getBody())
            .contains(HttpStatusCode.BAD_REQUEST.getMessage().getBytes(StandardCharsets.UTF_8));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    @DisplayName("Exception을 상속받은 Exception이 Cause일 때, Internal Server Error반환")
    @Test
    void handleException_CauseOfExtendsException_BadRequest() {
        RuntimeException exception
            = makeRuntimeExceptionWithCause(new childOfException());
        HttpResponse httpResponse
            = controllerAdvice.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getProtocol()).isEqualTo(httpRequest.getProtocol());
        assertThat(httpResponse.getHttpStatusCode())
            .isEqualTo(HttpStatusCode.INTERNAL_SERVER_ERROR);
        byte[] expectedBody = HttpStatusCode.INTERNAL_SERVER_ERROR.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    private RuntimeException makeRuntimeExceptionWithCause(Exception causeException) {
        RuntimeException runtimeException = new RuntimeException();
        runtimeException.initCause(causeException);
        return runtimeException;
    }
}