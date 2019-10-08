package webserver.httpelement;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {
    @Test
    void initTest() {
        assertThat(HttpPath.of("/").get().toString()).isEqualTo("/");
        assertThat(HttpPath.of("/api").get().toString()).isEqualTo("/api");
        assertThat(HttpPath.of("/kokkiri/coh").get().toString()).isEqualTo("/kokkiri/coh");
    }

    @Test
    void invalidInputTest() {
        assertThat(HttpPath.of("/ /")).isEqualTo(Optional.empty());
        assertThat(HttpPath.of("api")).isEqualTo(Optional.empty());
    }
}