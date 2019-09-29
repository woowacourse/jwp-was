package http;

import http.exception.MediaTypeNotDefinedException;
import org.junit.jupiter.api.Test;

import static http.MediaType.ALL;
import static http.MediaType.HTML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MediaTypeTest {
    @Test
    void value와_일치하는_MediaType_반환() {
        assertThat(MediaType.of("text/html")).isEqualTo(HTML);
    }

    @Test
    void 일치하는_type이_없는_경우_예외_발생() {
        assertThatThrownBy(() -> MediaType.of("undefined"))
                .isInstanceOf(MediaTypeNotDefinedException.class);
    }

    @Test
    void tpye이_All인_경우_getValue호출시_html_type_반환() {
        assertThat(ALL.getValue()).isEqualTo("text/html");
    }

}