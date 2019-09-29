package webserver.request;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {
    @Test
    void create_headerMap_true(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "59");
        headers.put("Content-Type", "text/css");

        RequestHeader requestHeader = new RequestHeader(headers);
        assertThat(requestHeader.getHeader("Content-Length")).isEqualTo("59");
    }
}