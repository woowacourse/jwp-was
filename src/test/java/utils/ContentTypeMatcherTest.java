package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ContentTypeMatcherTest {
    @ParameterizedTest
    @MethodSource(value = "provideValidValue")
    @DisplayName("ContentTypeMatcher from 테스트")
    void match(String path, String expected) {
        assertThat(ContentTypeMatcher.match(path)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideValidValue() {
        return Stream.of(
            Arguments.of("/index.html", "text/html"),
            Arguments.of("/styles.css", "text/css"),
            Arguments.of("/blank", "application/octet-stream"),
            Arguments.of("/why.gif", "application/octet-stream")
        );
    }
}
