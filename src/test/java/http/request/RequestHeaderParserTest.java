package http.request;

import http.common.HttpHeader;
import http.parser.RequestHeaderParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequestHeaderParserTest {

    @Test
    void parse() {
        List<String> requestLines = Arrays.asList("Host: localhost:8080", "Connection: keep-alive");

        assertDoesNotThrow(() -> RequestHeaderParser.parse(requestLines));

        HttpHeader requestHeader = RequestHeaderParser.parse(requestLines);
        assertThat(requestHeader.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(requestHeader.getHeader("Connection")).isEqualTo("keep-alive");
    }

}