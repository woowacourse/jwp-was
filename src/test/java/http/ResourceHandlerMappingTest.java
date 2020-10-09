package http;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import webserver.ResourceHandlerMapping;

class ResourceHandlerMappingTest {
    private static Stream<Arguments> provideRequestLineAndResult() {
        return Stream.of(
                Arguments.of("GET /hello.js HTTP/1.1" + System.lineSeparator(), true),
                Arguments.of("GET /hello.css HTTP/1.1" + System.lineSeparator(), true),
                Arguments.of("GET /hello.html HTTP/1.1" + System.lineSeparator(), true),
                Arguments.of("GET /hello.woff HTTP/1.1" + System.lineSeparator(), true),
                Arguments.of("POST /hello.html HTTP/1.1" + System.lineSeparator(), false),
                Arguments.of("GET /hello HTTP/1.1" + System.lineSeparator(), false),
                Arguments.of("GET /create/user HTTP/1.1" + System.lineSeparator(), false)
        );
    }

    @DisplayName("matches: 일치하는 resource 확장자가 있는지 확인한다.")
    @MethodSource("provideRequestLineAndResult")
    @ParameterizedTest
    void matches(final String requestLine, final boolean expect) throws IOException {
        // given
        HttpRequest httpRequest = createHttpRequest(requestLine);
        ResourceHandlerMapping resourceHandlerMapping = new ResourceHandlerMapping();

        // when
        boolean actual = resourceHandlerMapping.matches(httpRequest);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private HttpRequest createHttpRequest(final String requestLine) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(requestLine.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return HttpRequest.from(bufferedReader);
    }
}
