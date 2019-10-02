package http.request;

import http.HttpHeaders;
import http.HttpVersion;
import http.exception.EmptyHttpRequestException;
import http.exception.RequestLineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    @DisplayName("HttpRequest GET 생성")
    void createGetRequest() throws IOException {
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
    @DisplayName("HttpRequest Post 생성")
    void createPostRequest() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        HttpHeaders httpHeaders = httpRequest.getHeaders();

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getUri().getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpHeaders.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpHeaders.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.getHeader("Content-Length")).isEqualTo("53");
        assertThat(httpHeaders.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpHeaders.getHeader("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getBody()).isEqualTo("userId=woowa&password=password&name=woo&email=woo@w.a");
    }

    @Test
    @DisplayName("Requset가 비어있을 때 EmptyHttpRequestException 발생")
    void emptyRequest() {
        InputStream in = new ByteArrayInputStream("".getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.makeHttpRequest(in))
                .isInstanceOf(EmptyHttpRequestException.class);
    }

    @Test
    @DisplayName("Request line parsing 결과가 3개가 아니면 예외 발생")
    void requestLineException() {
        String request = "GET /index.html\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream in = new ByteArrayInputStream(request.getBytes());

        assertThatThrownBy(() -> HttpRequestFactory.makeHttpRequest(in)).isInstanceOf(RequestLineException.class);
    }

    @Test
    @DisplayName("Query params GET (URL에 붙은 경우)")
    void QueryParams_GET() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);

        assertThat(httpRequest.getParam("userId")).isEqualTo("woowa");
        assertThat(httpRequest.getParam("password")).isEqualTo("password");
        assertThat(httpRequest.getParam("name")).isEqualTo("woo");
    }

    @Test
    @DisplayName("Query params GET (body에 붙은 경우)")
    void QueryParams_POST() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);

        assertThat(httpRequest.getParam("userId")).isEqualTo("woowa");
        assertThat(httpRequest.getParam("password")).isEqualTo("password");
        assertThat(httpRequest.getParam("name")).isEqualTo("woo");
    }

    @Test
    @DisplayName("Request Header에 Cookie가 있는 경우 Cookie 객체 생성")
    void getCookie() throws IOException {
        String request = "GET /index.html HTTP/1.1\n"
                + "Cookie: key1=value1;key2=value2";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);

        assertThat(httpRequest.matchCookie("key1", "value1")).isTrue();
        assertThat(httpRequest.matchCookie("key2", "value2")).isTrue();
    }
}