package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpVersionTest {
    static Stream<Arguments> httpVersionSource() {
        return Stream.of(
                Arguments.of("HTTP/1.1", HttpVersion.HTTP_1_1),
                Arguments.of("HTTP/2", HttpVersion.HTTP_2)
        );
    }

    @DisplayName("입력한 httpVersion을 반환하는지 테스트")
    @ParameterizedTest
    @MethodSource("httpVersionSource")
    void findTest(String requestVersion, HttpVersion actual) {
        HttpVersion expected = HttpVersion.find(requestVersion);

        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("지원하지 않는 httpVersion을 입력 시 예외 반환")
    @Test
    void findWithInvalidHttpVersionTest() {
        String requestVersion = "HTTP/0";

        assertThatThrownBy(() -> {
            HttpVersion.find(requestVersion);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
