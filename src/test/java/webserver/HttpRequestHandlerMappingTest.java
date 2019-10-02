package webserver;

import org.junit.jupiter.api.Test;
import webserver.httphandler.HttpRequestHandler;
import webserver.httphandler.HttpRequestHandlerMapping;
import webserver.httphandler.HttpResourceRequestHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpRequestHandlerMappingTest {
    private HttpRequestHandlerMapping httpRequestHandlerMapping = HttpRequestHandlerMapping.getInstance();

    @Test
    void handler_존재하는_경우() {
        assertDoesNotThrow(() -> httpRequestHandlerMapping.getHandler("/"));
    }

    @Test
    void handler_존재하지_않는_경우_디폴트가_HttpResourceRequestHandler() {
        HttpRequestHandler httpRequestHandler = httpRequestHandlerMapping.getHandler(null);
        assertThat(httpRequestHandler.getClass()).isEqualTo(HttpResourceRequestHandler.class);
    }
}