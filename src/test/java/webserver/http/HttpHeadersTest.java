package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        List<String> input = Arrays.asList("auth: 1", "bye: hey");

        HttpHeaders httpHeaders = HttpHeaders.from(input);

        assertThat(httpHeaders.get("auth")).isEqualTo("1");
        assertThat(httpHeaders.get("bye")).isEqualTo("hey");
    }
}