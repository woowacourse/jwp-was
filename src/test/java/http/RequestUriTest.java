package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestUriTest {
    @DisplayName("생성자 테스트")
    @Test
    void create() {
        String line = "GET /index.html HTTP/1.1";

        RequestUri requestUri = new RequestUri(line);

        assertAll(
                () -> assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestUri.getUri()).isEqualTo("/index.html")
        );
    }

    @Test
    void name() {
        RequestUri requestUri = new RequestUri("GET /user/create?userId=id&password=1234&email=email@email");

        Map<String, String> expect = new HashMap<>();
        expect.put("userId", "id");
        expect.put("password", "1234");
        expect.put("email", "email@email");

        assertThat(requestUri.makeRequestParam()).isEqualTo(expect);
    }
}
