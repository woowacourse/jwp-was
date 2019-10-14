package webserver.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpVersionTest {

    @ParameterizedTest
    @ValueSource(strings = {"HTTP/0.9", "HTTP/1.0", "HTTP/1.1"})
    @DisplayName("HTTP Version enum 객체를 제대로 불러오는지 확인")
    void of1(String version) {
        assertThat(HttpVersion.of(version).getVersion()).isEqualTo(version);
    }

    @Test
    @DisplayName("존재하지 않는 HTTP 버전을 호출 시 HTTP 1.1 enum 객체를 불러오는지 확인")
    void emptyHttpVersionOf() {
        assertThat(HttpVersion.of("아무개")).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    @DisplayName("HTTP 버전이 null일 때 HTTP 1.1 enum 객체를 불러오는지 확인")
    void nullHttpVersionOf() {
        assertThat(HttpVersion.of(null)).isEqualTo(HttpVersion.HTTP_1_1);
    }

    @Test
    void getVersion() {
        assertThat(HttpVersion.of("HTTP/1.1").getVersion()).isEqualTo("HTTP/1.1");
    }

}