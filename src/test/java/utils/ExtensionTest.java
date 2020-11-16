package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExtensionTest {
    @ParameterizedTest
    @MethodSource("provideExtensionAndResult")
    void getContentType(String extension, String result) {
        assertThat(Extension.getContentType(extension)).isEqualTo(result);
    }

    private static Stream<Arguments> provideExtensionAndResult() {
        return Stream.of(
            Arguments.of("js", "application/javascript;charset=utf-8"),
            Arguments.of("css", "text/css;charset=utf-8"),
            Arguments.of("html", "text/html;charset=utf-8"),
            Arguments.of("woff", null)
        );
    }
}
