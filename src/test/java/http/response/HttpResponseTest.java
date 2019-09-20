package http.response;

import http.common.HttpHeader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void serialize_확인() {
        StatusLine statusLine = new StatusLine("HTTP/1.1", ResponseStatus.OK);
        HttpHeader httpHeader = new HttpHeader(new ArrayList<>());

        HttpResponse httpResponse = new HttpResponse(statusLine, httpHeader, null);

        String httpResponseText = new String(httpResponse.serialize());

        assertThat(httpResponseText).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

}