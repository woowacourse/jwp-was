package http.response;

import http.common.HttpVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void serialize_확인() {
        HttpResponse httpResponse = new HttpResponse(HttpVersion.HTTP_1_1);
        httpResponse.setResponseStatus(ResponseStatus.OK);
        String httpResponseText = new String(httpResponse.serialize());

        assertThat(httpResponseText).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

}