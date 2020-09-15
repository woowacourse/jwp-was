package http.response;

import static http.HttpHeaderTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import http.request.ContentType;

class ResponseEntityTest {

    private static final String HTTP_RESPONSE_STRING = "HTTP/1.1 200 OK \n"
        + "Content-Type: text/html;charset=utf-8\n"
        + "Content-Length: 6902\n"
        + "\n"
        + "testBody";

    @Test
    public void construct() {
        String body = "testBody";
        Map<String, String> expectedHeader = new HashMap<String, String>(HEADERS) {{
            put("testKey", "testValue");
        }};

        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK)
            .version("HTTP/1.1")
            .addHeaders(HEADERS)
            .addHeader("testKey", "testValue")
            .body(body, ContentType.APPLICATION_X_WWW_FORM_URLENCODED);

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(responseEntity.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(responseEntity.getHttpHeader().getHeaders()).isEqualTo(expectedHeader),
            () -> assertThat(responseEntity.getHttpBody().getContent()).isEqualTo(body)
        );
    }

    @Test
    public void convertToString() {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK)
            .version("HTTP/1.1")
            .addHeaders(new HashMap<String, String>() {{
                put("Content-Type", "text/html;charset=utf-8");
                put("Content-Length", "6902");
            }})
            .body("testBody", ContentType.APPLICATION_X_WWW_FORM_URLENCODED);

        assertThat(responseEntity.convertToString()).isEqualTo(HTTP_RESPONSE_STRING);
    }
}