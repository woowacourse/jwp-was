package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static http.HttpVersion.HTTP_1_1;
import static http.MediaType.HTML;
import static http.response.HttpResponse.CRLF;
import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class HttpResponseTest {
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        response = HttpResponse.of(HTTP_1_1);
    }

    @Test
    void httpResponse_생성() {
        assertThatCode(() -> HttpResponse.of(HTTP_1_1)).doesNotThrowAnyException();
    }

    @Test
    void httpResponse_초기화() {
        assertThat(response.getMessageHeader())
                .isEqualTo(HTTP_1_1 + " " + OK.getMessage() + CRLF);
    }

    @Test
    void httpResponse_Redirect() {
        String redirected = "/index.html";

        response.redirect(redirected);

        assertThat(response.getMessageHeader())
                .isEqualTo("HTTP/1.1 302 Found\r\n"
                        + "Location: " + redirected + "\r\n");
    }

    @Test
    void hasBody() {
        byte[] body = "body".getBytes();

        response.setBody(body);

        assertThat(response.hasBody()).isTrue();
    }

    @Test
    void setBody() {
        byte[] body = "body".getBytes();
        ResponseBody responseBody = new ResponseBody(body, HTML);

        response.setBody(responseBody);

        assertThat(response.hasBody()).isTrue();
        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 200 OK" + CRLF
                + "Content-Type: text/html" + CRLF
                + "Content-Length: " + body.length + CRLF);
    }
}
