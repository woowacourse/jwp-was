package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MimeTypeTest {

    private static Stream<Arguments> provideUrl() {
        return Stream.of(
            Arguments.of("/index.html", MimeType.HTML),
            Arguments.of("/js.js", MimeType.JS),
            Arguments.of("/style.css", MimeType.CSS),
            Arguments.of("/favicon.ico", MimeType.ICO)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUrl")
    @DisplayName("url의 확장자를 기준으로 mimeType 탐색")
    void from(String url, MimeType mimeType) {
        MimeType expected = MimeType.from(url);

        assertThat(expected).isEqualTo(mimeType);
    }

    @Test
    @DisplayName("확장자가 없는경우 HTML로 설정")
    void fromWhenNoExtension() {
        String url = "/user/create";
        MimeType mimeType = MimeType.from(url);

        assertThat(mimeType).isEqualTo(MimeType.HTML);
    }

}
