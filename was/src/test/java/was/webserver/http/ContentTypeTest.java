package was.webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ContentTypeTest {
    static Stream<Arguments> contentTypeSource() {
        return Stream.of(
                Arguments.of("/index.html", ContentType.HTML),
                Arguments.of("/styles.css", ContentType.CSS),
                Arguments.of("/glyphicons-halflings-regular.woff", ContentType.WOFF),
                Arguments.of("/80-text.png", ContentType.PNG),
                Arguments.of("/bootstrap.min.js", ContentType.JAVASCRIPT)
        );
    }

    @DisplayName("요청 경로의 확장자에 맞는 ContentType 반환 테스트")
    @ParameterizedTest
    @MethodSource("contentTypeSource")
    void fromTest(String path, ContentType actual) {
        ContentType contentType = ContentType.from(path);

        assertThat(contentType).isEqualTo(actual);
    }

    @DisplayName("지원하지 않는 확장자 입력시 예외 반환")
    @Test
    void fromWithInvalidPathTest() {
        String invalidPath = "/index.txt";

        assertThatThrownBy(() -> {
            ContentType.from(invalidPath);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
