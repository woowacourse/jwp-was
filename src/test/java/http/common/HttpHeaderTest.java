package http.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpHeader httpHeader;

    @BeforeEach
    void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        br.readLine();
        httpHeader = HttpHeader.of(br);
    }

    @Test
    public void getHeaderTest() {
        assertThat(httpHeader.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeader.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeader.getHeader("Accept")).isEqualTo("*/*");
    }
}