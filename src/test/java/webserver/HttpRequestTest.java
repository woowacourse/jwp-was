package webserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private static final String TEST_REQUEST_DIRECTORY = "./src/test/resources/request/";

    @DisplayName("HttpRequest GET 요청")
    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @DisplayName("HttpRequest POST 요청")
    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_POST.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi");
    }
}
