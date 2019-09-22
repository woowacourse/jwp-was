package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeadersFactoryTest {

    @MethodSource("headersText")
    @ParameterizedTest(name = "{index}: {1}")
    void 파싱_테스트(String headersText, String message) throws IOException {
        //given
        final BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(headersText.getBytes())));

        // when
        final RequestHeaders requestHeaders = RequestHeadersFactory.generate(br);

        // then
        assertThat(requestHeaders.size()).isEqualTo(3);
        assertThat(requestHeaders.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(requestHeaders.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(requestHeaders.getHeader("Accept")).isEqualTo("*/*");
    }

    static Stream<Arguments> headersText() {
        return Stream.of(
                Arguments.of("Host: localhost:8080\nConnection: keep-alive\nAccept: */*\n\nCookie: */*\n", "빈줄 이후에 멈추는지 확인"),
                Arguments.of("Host: localhost:8080\nConnection: keep-alive\nAccept: */*\n", "정상 입력"),
                Arguments.of("Host: localhost:8080\nConnection: keep-alive\nAccept: */*", "마지막에 빈줄이 없는 경우")
        );
    }
}