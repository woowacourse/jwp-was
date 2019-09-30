package utils.parser;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderParserTest {
    final SimpleStringParser httpHeaderFieldsParser = KeyValueParserFactory.httpHeaderFieldsParser();

    @Test
    void parseHttpRequestHeader() {
        final String REQUEST_HEADER = "Host: localhost:8080\r\n" +
                                        "Connection: keep-alive\r\n" +
                                        "Accept: */*";
        final Map<String, String> result = httpHeaderFieldsParser.interpret(REQUEST_HEADER);
        assertThat(result.get("Host")).isEqualTo("localhost:8080");
        assertThat(result.get("Connection")).isEqualTo("keep-alive");
        assertThat(result.get("Accept")).isEqualTo("*/*");
    }

    @Test
    void parseInvalidHttpRequestHeader() {
        String REQUEST_HEADER = "Host: localhost:8080 Connection: keep-alive Accept: */*";
        Map<String, String> result = httpHeaderFieldsParser.interpret(REQUEST_HEADER);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get("Host")).isEqualTo("localhost:8080 Connection: keep-alive Accept: */*");
    }
}