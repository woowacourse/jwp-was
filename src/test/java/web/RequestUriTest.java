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

    @DisplayName("uri가 루트 경로인지 확인한다.")
    @Test
    void isRootPath() {
        RequestUri uri = new RequestUri("/");
        assertTrue(uri.isRootPath());
    }

    @DisplayName("uri에 담긴 파라미터를 추출한다.")
    @Test
    void getParameter() {
        Map<String, String> expected = new HashMap<String, String>(){
            {
                put("userId", "javajigi");
                put("password", "password");
                put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
                put("email", "javajigi%40slipp.net");
            }
        };
        RequestUri uri = new RequestUri("/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        Map<String, String> actual = uri.getParameters();

        assertEquals(expected, actual);
    }
}