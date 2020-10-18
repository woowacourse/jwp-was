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

import type.method.MethodType;

public class HttpRequestTest {

    private HttpRequest httpRequestGetMethod;
    private HttpRequest httpRequestPostMethod;
    private HttpRequest httpRequestPostMethodSpecialCase;

    @BeforeEach
    void setUp() throws IOException {
        String testDirectory = "./src/test/resources/";

        InputStream inGetMethod = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        InputStream inPostMethod = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        InputStream inPostMethodSpecialCase = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));

        BufferedReader brGetMethod = new BufferedReader(new InputStreamReader(inGetMethod,"UTF-8"));
        BufferedReader brPostMethod = new BufferedReader(new InputStreamReader(inPostMethod,"UTF-8"));
        BufferedReader brPostMethodSpecialCase = new BufferedReader(new InputStreamReader(inPostMethodSpecialCase,"UTF-8"));

        httpRequestGetMethod = HttpRequestParser.parse(brGetMethod);
        httpRequestPostMethod = HttpRequestParser.parse(brPostMethod);
        httpRequestPostMethodSpecialCase = HttpRequestParser.parse(brPostMethodSpecialCase);
    }

    @Test
    void getMethod() {
        assertThat(httpRequestGetMethod.getMethod()).isEqualTo(MethodType.GET);

        assertThat(httpRequestPostMethod.getMethod()).isEqualTo(MethodType.POST);

        assertThat(httpRequestPostMethodSpecialCase.getMethod()).isEqualTo(MethodType.POST);
    }

    @Test
    void getUrl() {
        assertThat(httpRequestGetMethod.getUrl()).isEqualTo("/user/create");

        assertThat(httpRequestPostMethod.getUrl()).isEqualTo("/user/create");

        assertThat(httpRequestPostMethodSpecialCase.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getHttpRequestHeaderByName() {
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");

        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void getHttpRequestParamsByName() {
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("password")).isEqualTo("password");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("name")).isEqualTo("JaeSung");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestParamsByName("id")).isEqualTo("1");
    }

    @Test
    void getHttpRequestBodyByName() {
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("name")).isEqualTo("JaeSung");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("name")).isEqualTo("JaeSung");
    }

    @Test
    void getContentType() {
        assertThat(httpRequestGetMethod.getContentType()).isEqualTo("*/*");

        assertThat(httpRequestPostMethod.getContentType()).isEqualTo("*/*");

        assertThat(httpRequestPostMethodSpecialCase.getContentType()).isEqualTo("*/*");
    }
}