package http.supoort;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResolverMappingTest {

    @Test
    void 일치() {
        assertThat(new ResolverMapping("\\/.*\\.html").match("/index.html")).isTrue();
    }

    @Test
    void 불일치() {
        assertThat(new ResolverMapping("\\/.*\\.html").match("/css/bootstrap.min.css")).isFalse();
    }
}