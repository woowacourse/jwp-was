package http.response;

import http.common.HttpVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private static final String INDEX_HTML = "./templates/index.html";
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse();
    }

    @Test
    void index페이지_forward() {
        httpResponse.forward(INDEX_HTML);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);

        assertThat(httpResponse.getHeader("Content-Length")).isEqualTo(0);
        assertThat(httpResponse.getHeader("Content-Type")).isEqualTo("text/html");
    }

    @Test
    void index페이지_redirect() {
        httpResponse.redirect(INDEX_HTML);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);

        assertThat(httpResponse.getHeader("Location")).isEqualTo(INDEX_HTML);
    }
}