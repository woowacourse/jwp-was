package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MimeTypeTest {
    @ParameterizedTest
    @MethodSource(value = "provideValidValue")
    @DisplayName("MimeType from 테스트")
    void from(String path, MimeType expected) {
        assertThat(MimeType.from(path)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideValidValue() {
        return Stream.of(
            Arguments.of("/index.html", MimeType.TEXT_HTML),
            Arguments.of("/styles.css", MimeType.TEXT_CSS),
            Arguments.of("/blank", MimeType.APPLICATION_OCTET_STREAM),
            Arguments.of("/why.gif", MimeType.APPLICATION_OCTET_STREAM)
        );
    }
}
