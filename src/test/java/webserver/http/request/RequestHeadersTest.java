package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeadersTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        List<String> input = Arrays.asList("auth: 1", "bye: hey");

        RequestHeaders requestHeaders = RequestHeaders.from(input);

        assertThat(requestHeaders.get("auth")).isEqualTo("1");
        assertThat(requestHeaders.get("bye")).isEqualTo("hey");
    }
}