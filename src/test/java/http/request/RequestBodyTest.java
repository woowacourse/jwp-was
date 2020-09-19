package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.AbstractHttpRequestGenerator;

class RequestBodyTest extends AbstractHttpRequestGenerator {

    @DisplayName("RequestBody 조회")
    @Test
    void check_RequestBody_Content() throws IOException {
        HttpRequest httpRequest = createHttpPostRequest("POST_UserCreateRequest");
        RequestBody requestBody = httpRequest.getRequestBody();
        Map<String, String> parsedBody = requestBody.parseBody();

        assertAll(
            () -> assertThat(parsedBody.get("userId")).isEqualTo("sonypark"),
            () -> assertThat(parsedBody.get("password")).isEqualTo("sony123"),
            () -> assertThat(parsedBody.get("name")).isEqualTo("sony"),
            () -> assertThat(parsedBody.get("email")).isEqualTo("sonypark0204@gmail.com")
        );

    }
}
