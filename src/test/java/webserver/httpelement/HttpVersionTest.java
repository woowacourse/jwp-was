package webserver.httpelement;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {
    @Test
    void initTest() {
        assertThat(HttpVersion.of("HTTP/0.9").get()).isEqualTo(HttpVersion.HTTP_0_9);
        assertThat(HttpVersion.of("HTTP/ 1.0").get()).isEqualTo(HttpVersion.HTTP_1_0);
        assertThat(HttpVersion.of("HTTP / 1.1").get()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(HttpVersion.of("HTTP /2").get()).isEqualTo(HttpVersion.HTTP_2_0);
    }

    @Test
    void invalidInputTest() {
        assertThat(HttpVersion.of("HTTP1.0")).isEqualTo(Optional.empty());
        assertThat(HttpVersion.of("1.1")).isEqualTo(Optional.empty());
        assertThat(HttpVersion.of("HTTP/2.9")).isEqualTo(Optional.empty());
    }
}