package dev.luffy.http;

import dev.luffy.http.request.HttpRequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestMappingTest {

    @DisplayName("두 RequestMapping 객체가 같으려면 method 와 path 가 모두 동일하다.")
    @Test
    void name() {
        assertEquals(
                new RequestMapping(HttpRequestMethod.GET, "/"),
                new RequestMapping(HttpRequestMethod.GET, "/")
        );
    }

    @DisplayName("path 가 동일하고 method 가 다르면 동일한 객체가 아니다.")
    @Test
    void name2() {
        assertNotEquals(
                new RequestMapping(HttpRequestMethod.POST, "/"),
                new RequestMapping(HttpRequestMethod.GET, "/")
        );
    }

    @DisplayName("path 가 다르고 method 가 동일하면 동일한 객체가 아니다.")
    @Test
    void name3() {
        assertNotEquals(
                new RequestMapping(HttpRequestMethod.GET, "/go"),
                new RequestMapping(HttpRequestMethod.GET, "/back")
        );
    }
}