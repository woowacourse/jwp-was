package http.model;

import http.exceptions.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ContentTypeTest {

    @Test
    void 확장자로_ContentType_가져오는지() {
        String extension = "html";
        assertThat(ContentType.of(extension)).isEqualTo(ContentType.HTML);

        String upperExtension = "HTML";
        assertThat(ContentType.of(upperExtension)).isEqualTo(ContentType.HTML);
    }

    @Test
    void MIME_TYPE으로_ContentType_가져오는지() {
        String mime = "text/html";
        assertThat(ContentType.from(mime)).isEqualTo(ContentType.HTML);
    }

    @Test
    void 존재하지_않는_확장자() {
        String extension = "xls";

        assertThatThrownBy(() -> ContentType.of(extension)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 존재하지_않는_MIME_TYPE() {
        String mime = "application/vnd.ms-excel";
        assertThatThrownBy(() -> ContentType.from(mime)).isInstanceOf(IllegalHttpRequestException.class);
    }
}