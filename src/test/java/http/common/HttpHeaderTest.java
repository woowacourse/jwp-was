package http.common;

import http.parser.RequestHeaderParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HttpHeaderTest {

    private List<String> headers = Arrays.asList("Host: localhost:8080",
        "Connection: keep-alive",
        "Accept: */*");

    @Test
    void create_request_header() {
        Map<String, String> header = new HashMap<>();
        headers.forEach(params -> {
            String[] paramTokens = params.split(":", 2);
            header.put(paramTokens[0].trim(), paramTokens[1].trim());
        });

        assertDoesNotThrow(() -> new HttpHeader(header));
    }

    @Test
    void find_header() {
        HttpHeader httpHeader = RequestHeaderParser.parse(headers);
        assertThat(httpHeader.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void put_header() {
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.putHeader("Host", "Conas");
        assertThat(httpHeader.getHeader("Host")).isEqualTo("Conas");
    }
}