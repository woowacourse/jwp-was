package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private HttpRequest httpRequestGetMethod;
    private HttpRequest httpRequestPostMethod;

    @BeforeEach
    void setUp() throws IOException {
        String testDirectory = "./src/test/resources/";

        InputStream inGetMethod = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        InputStream inPostMethod = new FileInputStream(new File(testDirectory + "Http_POST.txt"));

        BufferedReader brGetMethod = new BufferedReader(new InputStreamReader(inGetMethod,"UTF-8"));
        BufferedReader brPostMethod = new BufferedReader(new InputStreamReader(inPostMethod,"UTF-8"));

        httpRequestGetMethod = HttpRequestParser.parse(brGetMethod);
        httpRequestPostMethod = HttpRequestParser.parse(brPostMethod);
    }

    @Test
    void getUri() {
        assertThat(httpRequestGetMethod.getUrl()).isEqualTo("/user/create");
        assertThat(httpRequestPostMethod.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getHttpRequestHeaderByName() {
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void getHttpRequestParamsByName() {
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("password")).isEqualTo("password");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("name")).isEqualTo("JaeSung");
    }

    @Test
    void getHttpRequestBodyByName() {
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("name")).isEqualTo("JaeSung");
    }
}