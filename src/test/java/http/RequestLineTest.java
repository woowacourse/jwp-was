package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestLineTest {
    @DisplayName("from: 헤더 첫 라인을 입력받아 인스턴스가 생성된다.")
    @Test
    void from() throws IOException {
        // given
        String line = "GET / HTTP/1.1";

        // when
        RequestLine requestLine = RequestLine.from(line);

        // then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getUri().getPath()).isEqualTo("/"),
                () -> assertThat(requestLine.getUri().getQueryParameters()).isNull(),
                () -> assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1")
        );
    }

    @DisplayName("from: 잘못된 헤더 첫 라인 입력시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "/ HTTP/1.1", "GET / "})
    void from_InvalidLineFormat_ExceptionThrown(final String line) throws IOException {
        // given
        // when
        // then
        assertThatThrownBy(() -> RequestLine.from(line))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("request line 형식이 올바르지 않습니다.");
    }
}
