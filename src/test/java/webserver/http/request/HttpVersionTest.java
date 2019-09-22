package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpVersionTest {

    @Test
    void 생성_테스트() {
        // given & when
        final HttpVersion httpVersion = HttpVersion.of("HTTP/1.1");

        // then
        assertThat(httpVersion == HttpVersion.HTTP_1_1).isTrue();
    }

    @Test
    void 없는_HttpVersion_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> HttpVersion.of("HTT/1.1"));
    }
}