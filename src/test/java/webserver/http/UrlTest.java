package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlTest {
    @DisplayName("생성자 테스트")
    @Test
    void ofTest() {
        String requestUrl = "/index.html";
        Url url = Url.of(requestUrl);

        assertThat(url.getUrl()).isEqualTo(requestUrl);
    }

    @DisplayName("url의 확장자 검사")
    @ParameterizedTest
    @CsvSource({"/index.html,.html", "/css/styles.css,.css", "/js/scripts.js,.js"})
    void isEndsWithTest(String path, String fileExtender) {
        Url url = Url.of(path);

        assertThat(url.isEndsWith(fileExtender)).isTrue();
    }
}
