package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.exception.EmptyHttpRequestException;
import http.exception.RequestLineException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    void HttpRequest_GET_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        HttpHeaders httpHeaders = httpRequest.getHeaders();

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getUri().getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.getHeader("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getBody()).isEqualTo("");
    }

    @Test
    void HttpRequest_POST_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        HttpHeaders httpHeaders = httpRequest.getHeaders();

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getUri().getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.getHeader("Content-Length")).isEqualTo("39");
        assertThat(httpHeaders.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpHeaders.getHeader("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getBody()).isEqualTo("userId=woowa&password=password&name=woo");
    }

    @Test
    void Request가_비어있을_때_예외_발생() {
        InputStream in = new ByteArrayInputStream("".getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.makeHttpRequest(in)).isInstanceOf(EmptyHttpRequestException.class);
    }

    @Test
    void request_line_parsing_결과가_3개가_아니면_예외_발생() {
        String request = "GET /index.html\n" + "Host: localhost:8080\n" +
                "Connection: keep-alive\n" + "Accept: */*";
        InputStream in = new ByteArrayInputStream(request.getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.makeHttpRequest(in)).isInstanceOf(RequestLineException.class);
    }

    @Test
    void QueryParams_GET() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        QueryParams queryParams = httpRequest.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void QueryParams_POST() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        QueryParams queryParams = httpRequest.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }
}