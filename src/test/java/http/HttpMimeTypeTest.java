package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.HttpMimeType.CSS;
import static http.HttpMimeType.HTML;
import static org.assertj.core.api.Assertions.assertThat;

class HttpMimeTypeTest {
    @Test
    @DisplayName("Accept의 첫번째 MIME Type 추출")
    void getMimeTypeFromAccept() {
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
        String extension = "html";

        HttpMimeType type = HttpMimeType.getMimeTypeFrom(accept, extension);

        assertThat(type).isEqualTo(HTML);
    }

    @Test
    @DisplayName("Accept가 */*인 경우 확장자를 보고 판단")
    void allAccept() {
        String accept = "*/*";
        String extension = "css";

        HttpMimeType type = HttpMimeType.getMimeTypeFrom(accept, extension);

        assertThat(type).isEqualTo(CSS);
    }
}