package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestHeaderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHeaderTest.class);
    private static final String REQUEST_HEADER = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    private HttpRequestHeader httpRequestHeader;
    private InputStream inputStream;

    @BeforeEach
    private void setUp() {
        inputStream = new ByteArrayInputStream(REQUEST_HEADER.getBytes(StandardCharsets.UTF_8));
        httpRequestHeader = new HttpRequestHeader(inputStream);
    }

    @DisplayName("Request line에서 path를 분리한다.")
    @Test
    void getPathTest() throws IOException {
        String path = httpRequestHeader.getPath();

        assertThat(path).isEqualTo("/index.html");
    }


    @DisplayName("HTTP Request Header의 모든 라인을 출력한다.")
    @Test
    void printAllHttpRequestHeaderTest() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                return;
            }
            LOGGER.info(line);
            line = bufferedReader.readLine();
        }
    }
}
