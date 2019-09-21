package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestFirstLineTest {

    private static final String GET = "GET";
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String URI = "/user/hello";
    private static final String PARAMS = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final RequestFirstLine PARAMS_REQUEST_FIRST_LINE = new RequestFirstLine(String.format("%s %s?%s %s", GET, URI, PARAMS, HTTP_1_1));
    private static final RequestFirstLine NO_PARAMS_REQUEST_FIRST_LINE = new RequestFirstLine(String.format("%s %s %s", GET, URI, HTTP_1_1));

    @DisplayName("성공적으로 RequestFirstLine 객체를 생성한다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new RequestFirstLine(String.format("%s %s %s", GET, URI, HTTP_1_1)));
    }

    @DisplayName("RequestFirstLine 객체를 생성에 실패한다.")
    @Test
    void constructorFailed() {
        assertThrows(IllegalArgumentException.class, () -> new RequestFirstLine("ADFAFE " + HTTP_1_1));
    }

    @DisplayName("RequestFirstLine 객체의 Method 를 가져온다.")
    @Test
    void getMethod() {
        assertEquals(PARAMS_REQUEST_FIRST_LINE.getMethod(), GET);
    }

    @DisplayName("RequestFirstLine 객체의 FullUri 를 가져온다.")
    @Test
    void getFullUri() {
        assertEquals(PARAMS_REQUEST_FIRST_LINE.getFullUri(), String.format("%s?%s", URI, PARAMS));
    }

    @DisplayName("RequestFirstLine 객체의 Version 을 가져온다.")
    @Test
    void getVersion() {
        assertEquals(PARAMS_REQUEST_FIRST_LINE.getVersion(), HTTP_1_1);
    }

    @DisplayName("RequestFirstLine 객체의 parameter 있는지 확인한다.")
    @Test
    void hasParamsTrue() {
        assertTrue(PARAMS_REQUEST_FIRST_LINE.hasParams());
    }

    @DisplayName("RequestFirstLine 객체의 parameter 없는지 확인한다.")
    @Test
    void hasParamsFalse() {
        assertFalse(NO_PARAMS_REQUEST_FIRST_LINE.hasParams());
    }

    @DisplayName("RequestFirstLine 객체의 search 를 추출한다.")
    @Test
    void getSearchSuccess() {
        assertEquals(PARAMS_REQUEST_FIRST_LINE.getSearch(), PARAMS);
    }

    @DisplayName("RequestFirstLine 객체의 search 가 없을 때, search 를 추출에 실패한다.")
    @Test
    void getSearchFailed() {
        assertThrows(IllegalArgumentException.class, NO_PARAMS_REQUEST_FIRST_LINE::getSearch);
    }
}