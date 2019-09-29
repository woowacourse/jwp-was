package http.response;

import http.common.HttpHeader;
import http.common.HttpSession;
import http.common.HttpVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private static final String INDEX_HTML = "./templates/index.html";
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse(new HttpSession(UUID.randomUUID()));
    }

    @Test
    void index페이지_forward() {
        httpResponse.forward(INDEX_HTML);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);

        HttpHeader httpResponseHeader = httpResponse.getHttpHeader();
        assertThat(httpResponseHeader.getContentLength()).isEqualTo(0);
        assertThat(httpResponseHeader.get("Content-Type")).isEqualTo("text/html");
    }

    @Test
    void index페이지_redirect() {
        httpResponse.redirect(INDEX_HTML);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);

        HttpHeader httpResponseHeader = httpResponse.getHttpHeader();
        assertThat(httpResponseHeader.get("Location")).isEqualTo(INDEX_HTML);
    }

    @Test
    @DisplayName("get했을때 생성되는 seesionId가 주입한 UUID와 같은지 테스트")
    void 세션_id_생성_테스트() {
        UUID uuid = UUID.randomUUID();
        HttpResponse httpResponse = new HttpResponse(new HttpSession(uuid));
        HttpSession httpSession = httpResponse.getHttpSession();

        assertThat(httpSession.getSessionId()).isEqualTo(uuid.toString());
    }
}