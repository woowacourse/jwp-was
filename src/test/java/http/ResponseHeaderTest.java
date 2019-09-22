package http;

import http.response.ResponseHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderTest {

    @Test
    @DisplayName("200 Status HttpResponse")
    public void status200HttpResponse() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setStatusCode(200);
        responseHeader.setType("html");
        responseHeader.setUrl("/index.html");

        assertThat(responseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(responseHeader.getType()).isEqualTo("text/html");
        assertThat(responseHeader.isOk()).isTrue();
    }

    @Test
    @DisplayName("302 Status HttpResponse")
    public void status302HttpResponse() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setStatusCode(302);
        responseHeader.setType("html");
        responseHeader.setLocation("/index.html");
        responseHeader.setUrl("/user/create");

        assertThat(responseHeader.getUrl()).isEqualTo("/index.html");
        assertThat(responseHeader.getType()).isEqualTo("text/html");
        assertThat(responseHeader.isOk()).isFalse();
    }
}