package http.controller;

import http.supoort.IllegalRequestMappingException;
import http.supoort.RequestMapping;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AbstractControllerTest {
    private

    @Test
    void 리퀘스트매핑_중복없음() {
        assertDoesNotThrow(() -> new FileResourceController(RequestMapping.GET("/index.html")));
    }

    @Test
    void 리퀘스트매핑_중복() {
        assertThatThrownBy(() -> {
            new FileResourceController(RequestMapping.GET("/index.html"),
                    RequestMapping.GET("/index.html"));
        }).isInstanceOf(IllegalRequestMappingException.class);
    }
}