package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExtensionTest {
    @ParameterizedTest
    @MethodSource("provideJSAndAnswer")
    void isJS(String extension, boolean result) {
        assertThat(Extension.isJS(extension)).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("provideCSSAndAnswer")
    void isCSS(String extension, boolean result) {
        assertThat(Extension.isCSS(extension)).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("provideHTMLAndAnswer")
    void isHTML(String extension, boolean result) {
        assertThat(Extension.isHTML(extension)).isEqualTo(result);
    }

    private static Stream<Arguments> provideJSAndAnswer() {
        return Stream.of(
            Arguments.of("js", true),
            Arguments.of("css", false)
        );
    }

    private static Stream<Arguments> provideCSSAndAnswer() {
        return Stream.of(
            Arguments.of("css", true),
            Arguments.of("js", false)
        );
    }

    private static Stream<Arguments> provideHTMLAndAnswer() {
        return Stream.of(
            Arguments.of("html", true),
            Arguments.of("js", false)
        );
    }
}
