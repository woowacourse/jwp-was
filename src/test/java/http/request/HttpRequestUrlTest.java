package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestUrlTest {

    @DisplayName("HttpRequestUrl 의 path 를 성공적으로 추출한다.")
    @Test
    void getPath() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html");

        assertEquals(httpRequestUrl.getPath(), "/index.html");
    }

    @DisplayName("HttpRequestUrl 의 비어있는 Parameter 들을 성공적으로 추출한다.")
    @Test
    void getEmptyParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html?");

        assertEquals(httpRequestUrl.getParams(), "");
    }

    @DisplayName("params 가 없는 경우 getParams 메서드는 빈 문자열을 반환한다.")
    @Test
    void getParamsFail() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/index.html");

        assertEquals(httpRequestUrl.getParams(), "");
    }

    @DisplayName("HttpRequestUrl 의 parameter 들을 성공적으로 추출한다.")
    @Test
    void getParams() {
        HttpRequestUrl httpRequestUrl = HttpRequestUrl.of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertEquals(httpRequestUrl.getParams(), "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}