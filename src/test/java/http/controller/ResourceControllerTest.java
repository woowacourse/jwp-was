package http.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import http.AbstractHttpRequestGenerator;
import http.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;

class ResourceControllerTest extends AbstractHttpRequestGenerator {

    private Controller controller = new ResourceController(HttpRequestMapping.GET("/index.html"));

    @Test
    void response_200_OK() throws IOException {
        HttpRequest httpRequest = createHttpGetRequest("GET_IndexHTML");
        HttpResponse httpResponse = HttpResponse.from(new ByteArrayOutputStream());

        assertThat(controller.canHandle(httpRequest)).isTrue();

        controller.handle(httpRequest, httpResponse);

        assertAll(
            () -> assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(httpResponse.hasResource()).isTrue()
        );
    }
}
