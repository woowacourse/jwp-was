package webserver;

import exception.InvalidHttpVersionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpVersionTest {

    @DisplayName("HTTP/로 시작하는 version 값으로 HttpVersion 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"HTTP/1.0", "HTTP/1.1", "HTTP/2"})
    void httpVersionTest(String version) {
        assertThat(new HttpVersion(version)).isInstanceOf(HttpVersion.class);
    }

    @DisplayName("HTTP/로 시작하지 않는 version 값으로 HttpVersion 객체를 만들면 InvalidHttpVersionException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"HTTP 1.1", "http/2.0", "http v1"})
    void httpVersionExceptionTest(String version) {
        assertThatThrownBy(() -> new HttpVersion(version))
                .isInstanceOf(InvalidHttpVersionException.class)
                .hasMessage("처리할 수 없는 HTTP version입니다! -> " + version);
    }
}