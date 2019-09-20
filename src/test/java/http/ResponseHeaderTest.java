package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {

    @Test
    @DisplayName("200 Status HttpResponse")
    public void status200HttpResponse() {
        ResponseHeader responseHeader = new ResponseHeader(
                200, "html", null, "/index.html");

        assertThat(responseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(responseHeader.getType()).isEqualTo("text/html");
        assertThat(responseHeader.isOk()).isTrue();
    }

    @Test
    @DisplayName("302 Status HttpResponse")
    public void status302HttpResponse() {
        ResponseHeader responseHeader = new ResponseHeader(
                302, "html", "/index.html", "/user/create");

        assertThat(responseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(responseHeader.getType()).isEqualTo("text/html");
        assertThat(responseHeader.isOk()).isFalse();
    }
}