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

    private String testDirectory;

    @BeforeEach
    void setUp() {
        testDirectory = "./src/test/resources/";
    }

    @Test
    void HTTP_GET() throws IOException {
        // given
        InputStream inGetMethod = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader brGetMethod = new BufferedReader(new InputStreamReader(inGetMethod,"UTF-8"));

        // when
        HttpRequest httpRequestGetMethod = HttpRequestParser.parse(brGetMethod);

        // then
        assertThat(httpRequestGetMethod.getMethod()).isEqualTo(MethodType.GET);
        assertThat(httpRequestGetMethod.getUrl()).isEqualTo("/user/create");

        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestGetMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");

        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("password")).isEqualTo("password");
        assertThat(httpRequestGetMethod.getHttpRequestParamsByName("name")).isEqualTo("JaeSung");

        assertThat(httpRequestGetMethod.getContentType()).isEqualTo("*/*");
    }

    @Test
    void HTTP_POST() throws IOException {
        // given
        InputStream inPostMethod = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        BufferedReader brPostMethod = new BufferedReader(new InputStreamReader(inPostMethod,"UTF-8"));

        // when
        HttpRequest httpRequestPostMethod = HttpRequestParser.parse(brPostMethod);

        // then
        assertThat(httpRequestPostMethod.getMethod()).isEqualTo(MethodType.POST);
        assertThat(httpRequestPostMethod.getUrl()).isEqualTo("/user/create");

        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethod.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");

        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethod.getHttpRequestBodyByName("name")).isEqualTo("JaeSung");

        assertThat(httpRequestPostMethod.getContentType()).isEqualTo("*/*");
    }

    @Test
    void HTTP_POST2() throws IOException {
        // given
        InputStream inPostMethodSpecialCase = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        BufferedReader brPostMethodSpecialCase = new BufferedReader(new InputStreamReader(inPostMethodSpecialCase,"UTF-8"));

        // when
        HttpRequest httpRequestPostMethodSpecialCase = HttpRequestParser.parse(brPostMethodSpecialCase);

        // then
        assertThat(httpRequestPostMethodSpecialCase.getMethod()).isEqualTo(MethodType.POST);
        assertThat(httpRequestPostMethodSpecialCase.getUrl()).isEqualTo("/user/create");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestHeaderByName("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestHeaderByName("Connection")).isEqualTo("keep-alive");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestParamsByName("id")).isEqualTo("1");

        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("userId")).isEqualTo("javajigi");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("password")).isEqualTo("password");
        assertThat(httpRequestPostMethodSpecialCase.getHttpRequestBodyByName("name")).isEqualTo("JaeSung");

        assertThat(httpRequestPostMethodSpecialCase.getContentType()).isEqualTo("*/*");
    }
}