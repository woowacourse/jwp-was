package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineParserTest {

    @Test
    void parse_request_line() {
        String line = "GET /index.html HTTP/1.1";

        RequestLine requestLine = RequestLineParser.parse(line);
        assertThat(requestLine.getMethod()).isEqualByComparingTo(HttpMethod.GET);
        assertThat(requestLine.getUri()).isEqualTo(HttpUriParser.parse("/index.html"));
        assertThat(requestLine.getVersion()).isEqualByComparingTo(HttpVersion.HTTP_1_1);
    }

}