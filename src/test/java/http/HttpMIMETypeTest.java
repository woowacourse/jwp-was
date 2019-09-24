package http;

import org.junit.jupiter.api.Test;

import static http.HttpMIMEType.CSS;
import static http.HttpMIMEType.HTML;
import static org.assertj.core.api.Assertions.assertThat;

class HttpMIMETypeTest {
    @Test
    void Accept의_첫번째_MIME_TYPE_추출() {
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
        String extension = "html";

        HttpMIMEType type = HttpMIMEType.getMIMETypeFrom(accept, extension);

        assertThat(type).isEqualTo(HTML);
    }

    @Test
    void 해당하는_MIME_TYPE이_없는_경우_확장자를_보고_판단() {
        String accept = "*/*";
        String extension = "css";

        HttpMIMEType type = HttpMIMEType.getMIMETypeFrom(accept, extension);

        assertThat(type).isEqualTo(CSS);
    }
}