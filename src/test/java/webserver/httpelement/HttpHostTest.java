package webserver.httpelement;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHostTest {

    @Test
    void initTestA() {
        final HttpHost host = HttpHost.of(" localhost:8080").get();
        assertThat(host.toString()).isEqualTo("localhost:8080");
    }

    @Test
    void initTestB() {
        final HttpHost host = HttpHost.of("https://www.woowa.com:7080").get();
        assertThat(host.toString()).isEqualTo("https://www.woowa.com:7080");
    }

    @Test
    void ofInvalidInputTest() {
        assertThat(HttpHost.of("localhost 8080")).isEqualTo(Optional.empty());
    }
}