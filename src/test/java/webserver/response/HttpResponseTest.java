package webserver.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.response.ResponseStatus.*;

class HttpResponseTest {

    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse(OK, new ResponseHeaders(), new ResponseBody("/index.html"));
    }

    @Test
    void buildGetHeader_html() {
        httpResponse.buildGetHeader("html");
        assertThat(httpResponse.getHeader("Content-Type")).isEqualTo("text/html");
    }

    @Test
    void buildGetHeader_css() {
        httpResponse.buildGetHeader("css");
        assertThat(httpResponse.getHeader("Content-Type")).isEqualTo("text/css");
    }

    @Test
    void createRedirectResponse() {
        ResponseStatus found = FOUND;
        HttpResponse redirectResponse =
                new HttpResponse(found, new ResponseHeaders(), null);

        String location = "/index.html";
        redirectResponse.buildRedirectHeader(location);

        assertThat(redirectResponse.getHeader("Location")).isEqualTo(location);
        assertThat(redirectResponse.getResponseStatus()).isEqualTo(found);
    }

    @Test
    void responseBuilder() {
        httpResponse.buildGetHeader("html");
        assertThat(httpResponse.responseBuilder()).contains("HTTP/1.1 200 OK\r\n", "Content-Type: text/html\r\n");
    }
}