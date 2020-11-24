package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class URLTest {
    @DisplayName("생성자 테스트")
    @Test
    void ofTest() {
        String requestUrl = "/index.html";
        URL url = URL.of(requestUrl);

        assertThat(url.getPath()).isEqualTo(requestUrl);
    }

    @DisplayName("url의 확장자 검사")
    @ParameterizedTest
    @CsvSource({"/index.html,.html", "/css/styles.css,.css", "/js/scripts.js,.js"})
    void isEndsWithTest(String path, String fileExtender) {
        URL url = URL.of(path);

        assertThat(url.isEndsWith(fileExtender)).isTrue();
    }
}
