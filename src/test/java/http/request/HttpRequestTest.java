package http.request;

import http.common.HttpVersion;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private String testResources = "./src/test/resources";

    @Test
    void get_request_정상_요청() throws IOException {
        InputStream in = new FileInputStream(testResources + "/http_get.txt");

        HttpRequest httpRequest = HttpRequestParser.parse(in);

        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpRequest.getUrl()).isEqualTo(new Url("/user/create?userId=javajigi&password=password&name=JaeSung"));

        assertThat(httpRequest.getHttpHeader().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHttpHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHttpHeader().get("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getHttpRequestParams()).isEqualTo(HttpRequestParams.of("userId=javajigi&password=password&name=JaeSung"));
    }

    @Test
    void post_request_정상_요청() throws IOException {
        InputStream in = new FileInputStream(testResources + "/http_post.txt");

        HttpRequest httpRequest = HttpRequestParser.parse(in);

        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpRequest.getUrl()).isEqualTo(new Url("/user/create"));

        assertThat(httpRequest.getHttpHeader().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHttpHeader().get("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHttpHeader().get("Content-Length")).isEqualTo("46");
        assertThat(httpRequest.getHttpHeader().get("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpRequest.getHttpHeader().get("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getHttpRequestParams()).isEqualTo(HttpRequestParams.init());
    }
}
