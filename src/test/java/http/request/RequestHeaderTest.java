package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private RequestHeader requestHeader;

    @BeforeEach
    public void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        br.readLine();
        requestHeader = requestHeader.of(br);
    }

    @Test
    public void getHeaderTest() {
        assertThat(requestHeader.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(requestHeader.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(requestHeader.getHeader("Accept")).isEqualTo("*/*");
    }
}