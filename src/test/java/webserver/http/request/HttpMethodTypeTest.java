package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.HttpVersion;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpMethodTypeTest {
    static Stream<Arguments> httpVersionSource() {
        return Stream.of(
                Arguments.of("HTTP/1.1", HttpVersion.HTTP_1_1),
                Arguments.of("HTTP/2", HttpVersion.HTTP_2)
        );
    }

    @DisplayName("요청에 대해 적절한 httpVersion을 반환하는지 테스트")
    @ParameterizedTest
    @MethodSource("httpVersionSource")
    void findTest(String httpVersion, HttpVersion expected) {
        HttpVersion result = HttpVersion.find(httpVersion);

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("잘못된 httpVersion 입력 시 예외 반환")
    @Test
    void findWithInvalidHttpVersionTest() {
        assertThatThrownBy(() -> {
            HttpVersion.find("invalid http version");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
