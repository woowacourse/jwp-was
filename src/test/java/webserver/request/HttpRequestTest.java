package webserver.request;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.HttpVersion.HTTP_1_1;

class HttpRequestTest {

    @Test
    void confirmGetRequestContent() {
        HttpRequest normalGetRequest = getNormalGetRequest();
        assertThat(normalGetRequest.getHeader("Accept")).isEqualTo("*/*");
        assertThat(getNormalGetRequest().getVersion()).isEqualTo(HTTP_1_1);
        assertThat(getNormalGetRequest().getMethod()).isEqualTo("GET");

        assertThat(normalGetRequest.getParam("userId")).isEqualTo("javajigi");
        assertThat(normalGetRequest.getParam("password")).isEqualTo("password");
        assertThat(normalGetRequest.getParam("name")).isEqualTo("박재성");
        assertThat(normalGetRequest.getParam("email")).isEqualTo("javajigi@slipp.net");
    }

    private HttpRequest getNormalGetRequest() {
        List<String> lines = new ArrayList<>();
        lines.add("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        lines.add("Host: localhost:8080");
        lines.add("Connection: keep-alive");
        lines.add("Accept: */*");
        lines.add("");

        return new HttpRequest(lines);
    }

    @Test
    void confirmPostRequestContent() {
        HttpRequest requestWithContents = getNormalPostRequest();

        assertThat(requestWithContents.getPath()).isEqualTo("/user/create");

        assertThat(requestWithContents.getBody("userId")).isEqualTo("javajigi");
        assertThat(requestWithContents.getBody("password")).isEqualTo("password");
        assertThat(requestWithContents.getBody("name")).isEqualTo("박재성");
        assertThat(requestWithContents.getBody("email")).isEqualTo("javajigi@slipp.net");
    }

    private HttpRequest getNormalPostRequest() {
        List<String> lines = new ArrayList<>();
        lines.add("POST /user/create HTTP/1.1");
        lines.add("Host: localhost:8080");
        lines.add("Connection: keep-alive");
        lines.add("Content-Length: 93");
        lines.add("Accept: */*");
        lines.add("");
        lines.add("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        return new HttpRequest(lines);
    }
}