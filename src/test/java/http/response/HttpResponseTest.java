package http.response;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    private HttpRequest request;

    @BeforeEach
    void setUp() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "cookie_notExist_Http_Header.txt"));
        request = HttpRequestFactory.createHttpRequest(in);
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(request);
        response.sendRedirect("/index.html");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void addHeader() {
        HttpResponse response = new HttpResponse(request);
        response.addHeader("Content-Length", "123");
        assertThat(response.getHeader("Content-Length")).isEqualTo("123");
    }

    @Test
    void forward() {
        HttpResponse response = new HttpResponse(request);
        response.forward("/index.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");

    }
}