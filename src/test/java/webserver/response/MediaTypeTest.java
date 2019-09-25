package webserver.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MediaTypeTest {
    @Test
    void name() {
        // normal
        assertThat(MediaType.of("html")).isEqualTo(MediaType.TEXT_HTML_VALUE);

        // does not exist extension
        assertThat(MediaType.of("zzz")).isEqualTo(MediaType.DEFAULT);
    }
}