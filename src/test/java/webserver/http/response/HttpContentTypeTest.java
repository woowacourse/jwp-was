package webserver.http.response;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpContentTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/form.html"})
    void html_content_type_convert(String url) {
        assertEquals("text/html", HttpContentType.findContentType(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/style.css", "/css/bootstrap.min.css"})
    void css_content_type_convert(String url) {
        assertEquals("text/css", HttpContentType.findContentType(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/js/index.js", "/js/jquery.2-0-0.min.js"})
    void js_content_type_convert(String url) {
        assertEquals("text/javascript", HttpContentType.findContentType(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/favicon.ico", "/images/a.png", "/images/b.jpg", "/images/c.jpeg"})
    void image_content_type_convert(String url) {
        assertEquals("image/*", HttpContentType.findContentType(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/font/a.svg", "/font/b.woff", "/font/c.woff2", "/font/d.ttf", "/font/e.eot"})
    void font_content_type_convert(String url) {
        assertEquals("application/*; image/*", HttpContentType.findContentType(url));
    }
}