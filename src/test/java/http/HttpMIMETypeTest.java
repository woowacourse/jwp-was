package http;

import org.junit.jupiter.api.Test;

import static http.HttpMimeType.CSS;
import static http.HttpMimeType.HTML;
import static org.assertj.core.api.Assertions.assertThat;

class HttpMimeTypeTest {
    @Test
    void Accept의_첫번째_Mime_TYPE_추출() {
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
        String extension = "html";

        HttpMimeType type = HttpMimeType.getMimeTypeFrom(accept, extension);

        assertThat(type).isEqualTo(HTML);
    }

    @Test
    void 해당하는_Mime_TYPE이_없는_경우_확장자를_보고_판단() {
        String accept = "*/*";
        String extension = "css";

        HttpMimeType type = HttpMimeType.getMimeTypeFrom(accept, extension);

        assertThat(type).isEqualTo(CSS);
    }
}