package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import http.request.StartLine;

class StartLineTest {
    @Test
    void create() {
        StartLine startLine = StartLine.from("GET / HTTP/1.1");

        assertAll(
            () -> assertThat(startLine.getHttpMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(startLine.getPath()).isEqualTo("/"),
            () -> assertThat(startLine.getVersion()).isEqualTo("HTTP/1.1")
        );
    }

    @Test
    void createException() {
        assertAll(
            () -> assertThatThrownBy(() -> StartLine.from(null))
                .isInstanceOf(IllegalArgumentException.class),
            () -> assertThatThrownBy(() -> StartLine.from(""))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }
}