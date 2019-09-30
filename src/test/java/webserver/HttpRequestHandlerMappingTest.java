package webserver;

import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import webserver.httphandler.HttpRequestHandler;
import webserver.httphandler.HttpRequestHandlerMapping;
import webserver.httphandler.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.StringUtils.BLANK;

class HttpRequestHandlerMappingTest {
    private HttpRequestHandlerMapping httpRequestHandlerMapping = HttpRequestHandlerMapping.getInstance();

    @Test
    void handler_존재하는_경우() {
        assertDoesNotThrow(() -> httpRequestHandlerMapping.getHandler("/"));
    }

    @Test
    void handler_존재하지_않는_경우() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("POST /user/login HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        HttpRequestHandler handler = httpRequestHandlerMapping.getHandler(null);
        assertThrows(ResourceNotFoundException.class, () -> handler.handleHttpRequest(httpRequest, httpResponse));
    }
}