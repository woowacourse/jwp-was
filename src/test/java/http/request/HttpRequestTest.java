package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpRequest httpRequest;

    public void createRequest(String method) {
        try {
            InputStream in = new FileInputStream(new File(String.format("%sHttp_%s.txt", TEST_DIRECTORY, method)));
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            httpRequest = RequestHandler.getInstance().create(br);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("GET 요청 테스트")
    public void request_GET() {
        createRequest("GET");

        assertThat(httpRequest.isGet()).isTrue();
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
    }

    @Test
    @DisplayName("POST 요청 테스트")
    public void request_POST() {
        createRequest("POST");

        assertThat(httpRequest.isPost()).isTrue();
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
    }

    @Test
    @DisplayName("URL 테스트")
    public void getUrlTest() {
        createRequest("GET");

        assertThat(httpRequest.getUrl()).isEqualTo("http://localhost:8080/user/create");
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST"})
    @DisplayName("Query 존재 테스트")
    public void getQueryTest(String method) {
        createRequest(method);

        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("aiden");
        assertThat(httpRequest.getParameter("email")).isEqualTo("aiden@aiden.com");

        assertThat(httpRequest.getParameter("Host")).isEqualTo(null);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST"})
    @DisplayName("Path 테스트")
    public void getPathTest(String method) {
        createRequest(method);

        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("Header 테스트")
    public void getHeaderTest() {
        createRequest("GET");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }
}