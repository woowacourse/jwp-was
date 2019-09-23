package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpRequest httpRequest;

    @BeforeEach
    public void setUp() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
    }

    @Test
    public void request_GET() {
        assertThat(httpRequest.isGet()).isTrue();
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
    }

    @Test
    public void getUrlTest() {
        assertThat(httpRequest.getUrl()).isEqualTo("http://localhost:8080/user/create");
    }

    @Test
    public void getPathTest() {
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }

    @Test
    public void methodTest() {
        assertThat(httpRequest.isGet()).isTrue();
        assertThat(httpRequest.isPost()).isFalse();
    }

    @Test
    public void getHeaderTest() {
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    public void getParameterTest() {
        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("aiden");
        assertThat(httpRequest.getParameter("email")).isEqualTo("aiden@aiden.com");
    }
}