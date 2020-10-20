package web;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("HttpRequest 생성 - GET")
    @Test
    public void createGetRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.from(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeaders("Connection")).isEqualTo("keep-alive");
    }

    @DisplayName("HttpRequest 생성 - POST")
    @Test
    public void createPostRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeaders("Connection")).isEqualTo("keep-alive");
    }

    @DisplayName("HttpRequest 생성 - queryString")
    @Test
    public void createPostRequestWithQueryString() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        HttpRequest request = HttpRequest.from(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeaders("Connection")).isEqualTo("keep-alive");
        assertThat(request.getRequestBody().getParams()).containsEntry("id", "1");
        assertThat(request.getRequestBody().getParams()).containsEntry("userId", "javajigi");
    }
}