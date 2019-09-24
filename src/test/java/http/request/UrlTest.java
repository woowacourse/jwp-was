package http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlTest {

    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/form.html"})
    void html_url_생성(String url) {
        assertEquals(HttpRequestType.TEMPLATES.getPrefix() + url, new Url(url).getFullUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/style.css", "/css/bootstrap.min.css", "/js/index.js", "/favicon.ico", "/image/a.gif", "/font/a.svg", "/font/a.ttf"})
    void 정적_파일_파싱(String url) {
        assertEquals(HttpRequestType.STATIC.getPrefix() + url, new Url(url).getFullUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/user/create", "/read"})
    void 서버_로직_파싱(String url) {
        assertEquals(HttpRequestType.LOGIC.getPrefix() + url, new Url(url).getFullUrl());
    }
}