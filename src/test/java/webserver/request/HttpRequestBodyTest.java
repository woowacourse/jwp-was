package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpRequestBodyTest {

    private static final String PARAMS = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private RequestBody requestBody;

    @BeforeEach
    void setUp() {
        requestBody = new RequestBody();
        requestBody.put(PARAMS);
    }

    @DisplayName("성공적으로 RequestBody 의 doGet 메서드를 수행한다.")
    @Test
    void putAndGet() {
        assertEquals(requestBody.get("userId"), "javajigi");
        assertEquals(requestBody.get("password"), "password");
        assertEquals(requestBody.get("name"), "박재성");
        assertEquals(requestBody.get("email"), "javajigi@slipp.net");
    }

    @DisplayName("해당되는 key 가 존재하지 않는 RequestBody doGet 메서드는 실패한다.")
    @Test
    void getFailWhenNoKeyValuePair() {
        assertThrows(IllegalArgumentException.class, () -> requestBody.get("middle"));
    }
}