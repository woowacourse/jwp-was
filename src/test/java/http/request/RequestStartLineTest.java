package http.request;

import http.common.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class RequestStartLineTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private RequestStartLine requestStartLine;

    @BeforeEach
    void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        requestStartLine = RequestStartLine.of(br);
    }

    @Test
    public void getMethodTest() {
        assertThat(requestStartLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void getPathTest() {
        assertThat(requestStartLine.getPath()).isEqualTo("/user/create");
    }

    @Test
    public void getQueryStringTest() {
        assertThat(requestStartLine.getQueryString()).isEqualTo("userId=aiden&password=password&name=aiden&email=aiden@aiden.com");
    }

    @Test
    public void getProtocolTest() {
        assertThat(requestStartLine.getProtocol()).isEqualTo("http");
    }

    @Test
    public void getQueryTest() {
        assertThat(requestStartLine.getQuery().get("userId")).isEqualTo("aiden");
        assertThat(requestStartLine.getQuery().get("password")).isEqualTo("password");
        assertThat(requestStartLine.getQuery().get("name")).isEqualTo("aiden");
        assertThat(requestStartLine.getQuery().get("email")).isEqualTo("aiden@aiden.com");
    }
}