package webserver.protocol;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeadersParserTest {
    @DisplayName("정상적으로 RequestHeaders 생성")
    @Test
    void parseTest() throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");
        final RequestHeaders expected = new RequestHeaders(headers);

        final String data = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n"
            + System.lineSeparator();
        final StringReader stringReader = new StringReader(data);
        final BufferedReader reader = new BufferedReader(stringReader);

        assertThat(RequestHeadersParser.parse(reader))
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}