package http.model.response;

import http.exceptions.IllegalHttpRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ContentTypeTest {

    @Test
    void 확장자로_찾기() {
        assertThat(ContentType.of("js")).isEqualTo(ContentType.JS);
    }

    @Test
    void 확장자가_없을_경우() {
        assertThatThrownBy(() -> ContentType.of("excel")).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void MIME_타입으로_찾기() {
        assertThat(ContentType.from("text/html")).isEqualTo(ContentType.HTML);
    }

    @Test
    void MIME_없을_경우() {
        assertThatThrownBy(() -> ContentType.from("application/vnd.ms-excel")).isInstanceOf(IllegalHttpRequestException.class);
    }
}