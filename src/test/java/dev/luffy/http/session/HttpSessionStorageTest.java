package dev.luffy.http.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpSessionStorageTest {

    @DisplayName("HttpSessionStorage 가 성공적으로 생성된다.")
    @Test
    void getInstance() {
        assertNotNull(HttpSessionStorage.getInstance());
    }

    @DisplayName("createSession 이 정상적으로 동작한다.")
    @Test
    void createSession() {
        HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();
        httpSessionStorage.createSession("this_is_custom_id");
        assertNotNull(httpSessionStorage.getSession("this_is_custom_id"));
    }

    @DisplayName("removeSession 이 정상적으로 동작한다.")
    @Test
    void removeSession() {
        HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();
        httpSessionStorage.createSession("session_created");
        httpSessionStorage.removeSession("session_created");
        assertNull(httpSessionStorage.getSession("null"));
    }
}