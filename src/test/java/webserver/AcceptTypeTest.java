package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AcceptTypeTest {

    static Stream<Arguments> generateValue() {
        return Stream.of(
            Arguments.of("/index.html", AcceptType.HTML),
            Arguments.of("/css/bootstrap.min.css", AcceptType.CSS),
            Arguments.of("/css/styles.css", AcceptType.CSS),
            Arguments.of("/js/jquery-2.2.0.min.js", AcceptType.JS),
            Arguments.of("/fonts/glyphicons-halflings-regular.woff", AcceptType.WOFF)
        );
    }

    @ParameterizedTest
    @MethodSource("generateValue")
    void acceptTypeInitTest(String filePath, AcceptType expectedAcceptType) {
        assertThat(AcceptType.of(filePath)).isEqualTo(expectedAcceptType);
    }
}