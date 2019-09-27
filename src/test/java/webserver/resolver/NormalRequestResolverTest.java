package webserver.resolver;

import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NormalRequestResolverTest {

    @Test
    void resolve_예외생성() throws IOException {
        String request = "GET /exception HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(request, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        assertThrows(BadRequestException.class, () -> NormalRequestResolver.resolve(httpRequest, httpResponse));

    }
}