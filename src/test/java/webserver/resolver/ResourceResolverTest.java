package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.HttpRequestFixtureUtils;
import webserver.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceResolverTest {
    @Test
    void URL_Not_Found() {
        HttpRequest request = HttpRequestFixtureUtils.makeHttpRequestFixture("GET /foo.xyz HTTP/1.1");
        HttpResponse response = new HttpResponse(request);

        assertThrows(ResourceNotFoundException.class, () -> new ResourceResolver().resolve2(request, response));
    }
}