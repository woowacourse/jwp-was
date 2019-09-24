package http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/form.html"})
    void 템플릿_파싱(String url) {
        assertEquals(HttpRequestType.TEMPLATES, HttpRequestType.of(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/style.css", "/css/bootstrap.min.css", "/js/index.js", "/favicon.ico", "/image/a.gif", "/font/a.svg", "/font/a.ttf"})
    void 정적_파일_파싱(String url) {
        assertEquals(HttpRequestType.STATIC, HttpRequestType.of(url));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user/create", "/read"})
    void 서버_로직_파싱(String url) {
        assertEquals(HttpRequestType.LOGIC, HttpRequestType.of(url));
    }

}