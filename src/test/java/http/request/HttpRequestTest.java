package http.request;

import static org.assertj.core.api.Assertions.assertThat;

import http.HttpMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("HttpRequest 생성 테스트")
    void from() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = HttpRequest.from(in);
        assertThat(httpRequest.method()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.path()).isEqualTo("/users");
        assertThat(httpRequest.version()).isEqualTo("HTTP/1.1");
        assertThat(httpRequest.getParam("name")).isEqualTo("홍길동");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    @DisplayName("HttpRequest getBody 테스트")
    void getBody() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequest.from(in);
        System.out.println(httpRequest.getBody());
    }
}
