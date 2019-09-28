package webserver;

import org.junit.jupiter.api.Test;
import webserver.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerMappingTest {
    private HandlerMapping handlerMapping = HandlerMapping.getInstance();

    @Test
    void handler_존재하는_경우() {
        assertDoesNotThrow(() ->handlerMapping.getHandler("/"));
    }

    @Test
    void handler_존재하지_않는_경우() {
        assertThrows(ResourceNotFoundException.class, () -> handlerMapping.getHandler(null));
    }
}