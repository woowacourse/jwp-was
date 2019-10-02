package http.response;

import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringUtils.BLANK;

class HttpResponseTest {
    @Test
    void serialize_확인() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET / HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));

        HttpResponse httpResponse = new HttpResponse(httpRequest);
        httpResponse.setResponseStatus(ResponseStatus.OK);
        String httpResponseText = new String(httpResponse.serialize());

        assertThat(httpResponseText).contains("HTTP/1.1 200 OK\r\n");
    }
}