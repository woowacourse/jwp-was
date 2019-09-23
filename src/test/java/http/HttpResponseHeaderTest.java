package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseHeaderTest {

    @Test
    @DisplayName("200 Status HttpResponse")
    public void status200HttpResponse() {
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(
                200, "html", null, "/index.html");

        assertThat(httpResponseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(httpResponseHeader.getType()).isEqualTo("text/html");
        assertThat(httpResponseHeader.isOk()).isTrue();
    }

    @Test
    @DisplayName("302 Status HttpResponse")
    public void status302HttpResponse() {
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(
                302, "html", "/index.html", "/user/create");

        assertThat(httpResponseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(httpResponseHeader.getType()).isEqualTo("text/html");
        assertThat(httpResponseHeader.isOk()).isFalse();
    }
}