package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.exception.EmptyHttpRequestException;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    @Test
    void HttpRequest_GET_생성() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        HttpHeaders headers = request.getHeaders();

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getBody()).isEqualTo("");
    }

    @Test
    void HttpRequest_POST_생성() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST.txt");
        HttpHeaders headers = request.getHeaders();

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(headers.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getHeader("Content-Length")).isEqualTo("39");
        assertThat(headers.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headers.getHeader("Accept")).isEqualTo("*/*");
        assertThat(request.getBody()).isEqualTo("userId=woowa&password=password&name=woo");
    }

    @Test
    void Request가_비어있을_때_예외_발생() {
        InputStream in = new ByteArrayInputStream("".getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.getHttpRequest(in)).isInstanceOf(EmptyHttpRequestException.class);
    }

    @Test
    void QueryParams_GET() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET.txt");
        QueryParams queryParams = request.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void QueryParams_POST() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST.txt");
        QueryParams queryParams = request.getQueryParams();

        assertThat(queryParams.getParam("userId")).isEqualTo("woowa");
        assertThat(queryParams.getParam("password")).isEqualTo("password");
        assertThat(queryParams.getParam("name")).isEqualTo("woo");
    }

    @Test
    void isStaticContentRequest() throws IOException {
        HttpRequest request1 = TestResourceLoader.getHttpRequest("Http_GET.txt");
        HttpRequest request2 = TestResourceLoader.getHttpRequest("Http_GET_Static.txt");

        assertThat(request1.isStaticContentRequest()).isFalse();
        assertThat(request2.isStaticContentRequest()).isTrue();
    }
}