package webserver.http.request;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webserver.http.HttpVersion;

class HttpRequestTest {

    @Test
    void toRequestMapping() {
        String uri = "/user/create";
        HttpMethod method = HttpMethod.POST;
        HttpRequestLine requestLine = new HttpRequestLine(method,
                new RequestURI(uri, HttpParams.of("")), HttpVersion.HTTP_1_1);
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpHeader(emptyMap()), "");

        assertThat(httpRequest.toRequestMapping()).isEqualTo(new RequestMapping(uri, method));
    }
}