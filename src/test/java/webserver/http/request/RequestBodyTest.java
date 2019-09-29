package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    String rawBody = "userId=javajigi&password=password";

    @DisplayName("빈 바디")
    @Test
    void getBody_empty_equal() {
        RequestBody requestBody = new RequestBody("");
        assertThat(requestBody.getBodies()).isEmpty();
    }

    @DisplayName("null 바디")
    @Test
    void getBody_null_equal() {
        RequestBody requestBody = new RequestBody(null);
        assertThat(requestBody.getBodies()).isEmpty();
    }

    @DisplayName("정상 바디")
    @Test
    void getBody_common_equal() {
        RequestBody requestBody = new RequestBody(rawBody);
        Map<String, String> bodies = new HashMap<>();
        bodies.put("userId","javajigi");
        bodies.put("password","password");
        assertThat(requestBody.getBodies()).isEqualTo(bodies);
        assertThat(requestBody.getBody("userId")).isEqualTo(bodies.get("userId"));
        assertThat(requestBody.getBody("password")).isEqualTo(bodies.get("password"));
    }
}