package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HttpContentTypeTest {

    @DisplayName("확장자에 따른 ContentType 조회 - 성공")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, text/html ;charset=utf-8", "/css.css, text/css", "hello/index.js, text/javascript"})
    void findContentType_SuccessToFind(String uri, String expected) {
        assertThat(HttpContentType.findContentType(uri)).isEqualTo(expected);
    }

    @DisplayName("확장자에 따른 ContentType 조회 - 실패, 해당 확장자가 없는 경우")
    @Test
    void findContentType_NotExistExtension_ThrownException() {
        String uri = "/index.jsx";
        assertThatThrownBy(() -> HttpContentType.findContentType(uri)).isInstanceOf(IllegalArgumentException.class);
    }
}
