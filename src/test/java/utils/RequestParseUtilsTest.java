package utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParseUtilsTest {
    @Test
    void requestHeader를_리스트로_받아온다() throws IOException {
        BufferedReader requestHeader = new BufferedReader(
                new StringReader(
                        "GET /index.html HTTP/1.1\n" +
                                "Host: localhost:8080\n" +
                                "Connection: keep-alive\n" +
                                "Accept: */*"
                )
        );

        List<String> parsedHeaders = RequestParseUtils.parseRequestHeader(requestHeader);
        assertThat(parsedHeaders.get(0)).isEqualTo("GET /index.html HTTP/1.1");
        assertThat(parsedHeaders.get(1)).isEqualTo("Host: localhost:8080");
        assertThat(parsedHeaders.get(2)).isEqualTo("Connection: keep-alive");
        assertThat(parsedHeaders.get(3)).isEqualTo("Accept: */*");
    }

}