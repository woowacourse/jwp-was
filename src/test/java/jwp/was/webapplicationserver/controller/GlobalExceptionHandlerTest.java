package jwp.was.webapplicationserver.controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static jwp.was.util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.PARAMETERS_EMPTY;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static jwp.was.webserver.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import jwp.was.util.exception.ChildOfException;
import jwp.was.util.exception.ChildOfIllegalArgumentException;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
    private HttpRequest httpRequest
        = new HttpRequest(GET, URL_PATH_INDEX_HTML, PARAMETERS_EMPTY, HTTP_VERSION, HEADERS_EMPTY
    );

    @DisplayName("IllegalArgumentException이 Cause일 때, BadRequst 반환")
    @Test
    void handleException_CauseOfIllegalArgumentException_BadRequest() {
        RuntimeException exception
            = makeRuntimeExceptionWithCause(new IllegalArgumentException());
        HttpResponse httpResponse
            = globalExceptionHandler.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
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
            = makeRuntimeExceptionWithCause(new ChildOfIllegalArgumentException());
        HttpResponse httpResponse
            = globalExceptionHandler.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
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
            = globalExceptionHandler.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
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
            = makeRuntimeExceptionWithCause(new ChildOfException());
        HttpResponse httpResponse
            = globalExceptionHandler.handleCauseException(httpRequest, exception);

        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
        assertThat(httpResponse.getHttpStatusCode())
            .isEqualTo(HttpStatusCode.INTERNAL_SERVER_ERROR);
        byte[] expectedBody = HttpStatusCode.INTERNAL_SERVER_ERROR.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .contains(CONTENT_TYPE_TEXT_PLAIN);
    }

    private RuntimeException makeRuntimeExceptionWithCause(Exception causeException) {
        return new RuntimeException(causeException);
    }
}
