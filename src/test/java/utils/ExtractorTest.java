package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractorTest {
    private final String requestLine = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    @DisplayName("requestLine으로부터 method 추출 테스트")
    @Test
    void methodFromRequestLine() {
        assertThat(Extractor.methodFromRequestLine(requestLine)).isEqualTo("GET");
    }

    @DisplayName("requestLine으로부터 path 추출 테스트")
    @Test
    void pathFromRequestLine() {
        assertThat(Extractor.pathFromRequestLine(requestLine)).isEqualTo("/user/create");
    }

    @DisplayName("requestLine으로부터 param 추출 테스트")
    @Test
    void paramFromRequestLine() {
        assertThat(Extractor.paramFromRequestLine(requestLine)).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}
