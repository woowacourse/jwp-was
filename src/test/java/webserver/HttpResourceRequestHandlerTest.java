package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.httprequesthandler.HttpResourceRequestHandler;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResourceRequestHandlerTest {
    private HttpResourceRequestHandler httpResourceRequestHandler = HttpResourceRequestHandler.getInstance();

    @ParameterizedTest
    @MethodSource("providePathAndExpectations")
    void canHandle(String path, boolean expectation) {
        assertThat(httpResourceRequestHandler.canHandle(path)).isEqualTo(expectation);
    }

    private static Stream<Arguments> providePathAndExpectations() {
        return Stream.of(
                Arguments.of("a.css", true),
                Arguments.of("a.html", true),
                Arguments.of("a.png", true),
                Arguments.of("a.woff", true),
                Arguments.of("a.js", true),
                Arguments.of("/", false),
                Arguments.of("", false),
                Arguments.of("/user", false),
                Arguments.of("/index", false),
                Arguments.of(null, false)
        );
    }
}