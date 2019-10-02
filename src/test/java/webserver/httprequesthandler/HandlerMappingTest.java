package webserver.httprequesthandler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exception.HandlerNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerMappingTest {
    private HandlerMapping handlerMapping = HandlerMapping.getInstance();
    private HttpRequestHandler httpResourceRequestHandler = handlerMapping.getHandler("abc.js");

    @ParameterizedTest
    @ValueSource(strings = {"a.html", "b.css", "c.png", "d.js"})
    @DisplayName("정적 자원 요청")
    void ResourceHandlerMapping(String input) {
        assertEquals(handlerMapping.getHandler(input), httpResourceRequestHandler);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/user/form", "/user/login", "/user/create", "/user/list"})
    @DisplayName("동적 자원 요청")
    void ServletHandlerMapping(String input) {
        assertEquals(handlerMapping.getHandler(input), HttpServletRequestHandler.getInstance());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("등록되지 않은 url 요청 시 예외 발생")
    void HandlerNotFound(String input) {
        assertThrows(HandlerNotFoundException.class, () -> handlerMapping.getHandler(input));
    }
}