package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("HttpRequest 생성 - GET")
    @Test
    public void createGetRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpRequest.from(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeaders()).hasSize(3);
    }

    @DisplayName("HttpRequest 생성 - POST")
    @Test
    public void createPostRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeaders()).hasSize(5);
    }
}