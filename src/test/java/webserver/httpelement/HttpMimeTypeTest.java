package webserver.httpelement;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMimeTypeTest {
    @Test
    void initTest() {
        assertThat(HttpMimeType.of("image/bmp").get()).isEqualTo(HttpMimeType.IMAGE_BMP);
        assertThat(HttpMimeType.of("application/json").get()).isEqualTo(HttpMimeType.of("application/json").get());
        assertThat(HttpMimeType.of("text/css").get() == HttpMimeType.of("text/css").get()).isTrue();
    }

    @Test
    void initInvalidInputTest() {
        assertThat(HttpMimeType.of("image bmp")).isEqualTo(Optional.empty());
    }
}