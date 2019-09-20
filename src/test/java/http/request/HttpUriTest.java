package http.request;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpUriTest {
    @Test
    void 확장자를_가지고_있는_경우() throws UnsupportedEncodingException {
        HttpUri uri = new HttpUri("/create/index.html");

        assertThat(uri.hasExtension()).isTrue();
    }

    @Test
    void 확장자를_가지고_있지_않은_경우() throws UnsupportedEncodingException {
        HttpUri uri = new HttpUri("/create/user");

        assertThat(uri.hasExtension()).isFalse();
    }
}