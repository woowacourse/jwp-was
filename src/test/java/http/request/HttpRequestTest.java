package http.request;

import http.common.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpRequest request;

    @BeforeEach
    void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        request = new HttpRequest(br);
    }

    @Test
    public void request_GET() {
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getQuery("userId")).isEqualTo("aiden");
    }

    @Test
    public void getUrlTest() {
        assertThat(request.getUrl()).isEqualTo("http://localhost:8080/user/create");
    }

    @Test
    public void getQueryTest() {
        assertThat(request.getQuery("userId")).isEqualTo("aiden");
        assertThat(request.getQuery("password")).isEqualTo("password");
        assertThat(request.getQuery("name")).isEqualTo("aiden");
        assertThat(request.getQuery("email")).isEqualTo("aiden@aiden.com");
    }

    @Test
    public void getPathTest() {
        assertThat(request.getPath()).isEqualTo("/user/create");
    }

    @Test
    public void getMethodTest() {
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void getHeaderTest() {
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getHeader("Accept")).isEqualTo("*/*");
    }
}