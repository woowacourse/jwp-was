package http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {
    private static Stream<Arguments> provideRequestHeader() {
        return Stream.of(
                Arguments.of(Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*"),
                        "localhost:8080",
                        "keep-alive",
                        "*/*"),
                Arguments.of(Arrays.asList("Host: localhost:80", "Connection: keep-alive", "Accept: image/*"),
                        "localhost:80",
                        "keep-alive",
                        "image/*")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRequestHeader")
    public void requestHeaderTest(List<String> header, String host, String connection, String accept) {
        RequestHeader requestHeader = RequestHeader.of(header);

        assertThat(requestHeader.getHeader("Host")).isEqualTo(host);
        assertThat(requestHeader.getHeader("Connection")).isEqualTo(connection);
        assertThat(requestHeader.getHeader("Accept")).isEqualTo(accept);
    }
}