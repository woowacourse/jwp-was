package http.model.response;

import http.model.common.HttpProtocols;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ServletResponseTest {
    private OutputStream out = new ByteArrayOutputStream();
    private ServletResponse response;

    @BeforeEach
    void setUp() {
        response = new ServletResponse(out);
    }

    @Test
    void 기본생성후_HTTP1_1인지() {
        assertThat(response.getProtocols()).isEqualTo(HttpProtocols.HTTP1);
    }

    @Test
    void 뷰_없는_경우() {
        assertThat(response.hasResource()).isFalse();
    }

    @Test
    void 뷰_있는_경우() {
        response.setView("/index.html");
        assertThat(response.hasResource()).isTrue();
    }

    @Test
    void 모델_없는_경우() {
        assertThat(response.hasModel()).isFalse();
    }

    @Test
    void 모델_있는_경우() {
        response.setModel(new Object());
        assertThat(response.hasModel()).isTrue();
    }

    @Test
    void ok_처리후_상태가_200인지() {
        response.ok("/index.html");

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void redirect_처리후_상태가_302인지() {
        response.redirect("/index.html");

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }
}