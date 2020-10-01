package http.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import http.ContentType;

class ResponseEntityTest {

    private static final String HTTP_RESPONSE_STRING = "HTTP/1.1 200 OK \n"
        + "Content-Type: text/html;charset=utf-8\n"
        + "\n"
        + "testBody";

    @Test
    public void construct() {
        String body = "testBody";
        Map<String, String> expectedHeader = new HashMap<String, String>() {{
            put("Content-Type", "text/html;charset=utf-8");
            put("testKey", "testValue");
        }};

        ResponseEntity responseEntity = ResponseEntity.generateWithStatus(HttpStatus.OK)
            .addHeader("testKey", "testValue")
            .addHeader("Content-Type", ContentType.HTML.getType())
            .body(body);

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(responseEntity.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(responseEntity.getHttpHeader().getHeaders()).isEqualTo(expectedHeader),
            () -> assertThat(responseEntity.getHttpBody().getContent()).isEqualTo(body)
        );
    }

    @Test
    public void convertToString() {
        ResponseEntity responseEntity = ResponseEntity.generateWithStatus(HttpStatus.OK)
            .addHeader("Content-Type", ContentType.HTML.getType())
            .body("testBody");

        assertThat(responseEntity.convertToString()).isEqualTo(HTTP_RESPONSE_STRING);
    }
}