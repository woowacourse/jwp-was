package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    @DisplayName("BufferedReader로 객체 생성")
    @Test
    void from() throws IOException {
        // given
        String request = ""
                + "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n";
        StringReader stringReader = new StringReader(request);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        // then
        assertThat(HttpRequest.from(bufferedReader)).isInstanceOf(HttpRequest.class);
    }
}
