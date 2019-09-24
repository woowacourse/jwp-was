package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpContentTypeTest {
    @Test
    @DisplayName("정상적인 HttpContentType 객체를 생성한다.")
    void httpContentType() {
        String input = "text/html; charset=utf-8";

        HttpContentType httpContentType = HttpContentType.of(input).get();

        assertNotNull(httpContentType);
    }

    @Test
    @DisplayName("확장자에 해당하는 HttpContentType을 가져온다.")
    void extensionToContentType() {
        HttpContentType httpContentType1 = HttpContentType.extensionToContentType("jpg");
        HttpContentType httpContentType2 = HttpContentType.extensionToContentType("ico");

        assertThat(httpContentType1.toString()).isEqualTo("image/jpeg;charset=utf-8");
        assertThat(httpContentType2.toString()).isEqualTo("image/x-icon;charset=utf-8");
    }

    @Test
    @DisplayName("확장자에 해당하는 HttpContentType을 가져온다.")
    void extensionToContentType2() {
        HttpContentType httpContentType3 = HttpContentType.extensionToContentType("test");

        assertThat(httpContentType3.toString()).isEqualTo("text/plain;charset=utf-8");
    }
}