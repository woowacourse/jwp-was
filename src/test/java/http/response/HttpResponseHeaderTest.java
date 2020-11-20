package http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import http.Cookies;

class HttpResponseHeaderTest {

    @Test
    void addResponseHeader() throws CloneNotSupportedException {
        Cookies cookies = Cookies.from(null);
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader(cookies);
        httpResponseHeader.addResponseHeader("Content-type", "text/html");

        assertThat(httpResponseHeader.getValue("Content-type")).isEqualTo("text/html");
    }

    @Test
    void isEmptyCookie_True() throws CloneNotSupportedException {
        Cookies cookies = Cookies.from(null);
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader(cookies);

        assertThat(httpResponseHeader.isEmptyCookie()).isTrue();
    }

    @Test
    void isEmptyCookie_False() throws CloneNotSupportedException {
        Cookies cookies = Cookies.from(null);
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader(cookies);
        httpResponseHeader.addCookie("logined", "true");

        assertThat(httpResponseHeader.isEmptyCookie()).isFalse();
    }

    @Test
    void flatCookie() throws CloneNotSupportedException {
        Cookies cookies = Cookies.from(null);
        final HttpResponseHeader httpResponseHeader = new HttpResponseHeader(cookies);
        httpResponseHeader.addCookie("logined", "true");
        httpResponseHeader.addCookie("Path", "/");

        assertThat(httpResponseHeader.flatCookie()).isEqualTo("logined=true; Path=/");
    }
}