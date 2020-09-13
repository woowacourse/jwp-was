package http;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {

    @DisplayName("constructor: request header 생성")
    @Test
    void constructor() throws IOException {
        // given
        InputStream inputStream = new FileInputStream("./src/test/resources/http/RequestLine.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // when
        RequestLine requestLine = new RequestLine(bufferedReader);

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getUri()).isEqualTo("/");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}
