package web;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestUriTest {

    @DisplayName("uri가 정적 파일인지 확인한다.")
    @Test
    void isStaticFile() {
        RequestUri uri = new RequestUri("user/create/form.html");
        assertTrue(uri.isStaticFile());
    }

    @DisplayName("요청 자원의 경로를 확인한다.")
    @Test
    public void findPath() {
        String expected = ResourceMatcher.JS.getResourcePath();
        String actual = new RequestUri("js/script.js").findPath();
        assertEquals(expected, actual);
    }

    @DisplayName("요청 자원의 contentType을 확인한다.")
    @Test
    public void getContentType() {
        String expected = ResourceMatcher.JS.getContentType();
        String actual = new RequestUri("js/script.js").findContentType();
        assertEquals(expected, actual);
    }
    @DisplayName("uri에 담긴 파라미터를 추출한다.")
    @Test
    void getParameter() {
        Map<String, String> expected = new HashMap<String, String>() {
            {
                put("userId", "javajigi");
                put("password", "password");
                put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
                put("email", "javajigi%40slipp.net");
            }
        };
        RequestUri uri = new RequestUri(
                "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        Map<String, String> actual = uri.getParameters();

        assertEquals(expected, actual);
    }
}