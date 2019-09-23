package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("Version 을 확인한다.")
    @Test
    void getVersion() {
        assertThat(getNormalGetRequest().getVersion()).isEqualTo("HTTP/1.1");
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

    @DisplayName("Header 를 확인한다.")
    @Test
    void getHeader() {
        assertThat(getNormalGetRequest().getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("Method 를 확인한다.")
    @Test
    void getMethod() {
        assertThat(getNormalGetRequest().getMethod()).isEqualTo("GET");
    }

    @DisplayName("Param 을 확인한다.")
    @Test
    void getParam() {
        HttpRequest httpRequest = getNormalGetRequest();
        assertThat(httpRequest.getParam("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParam("password")).isEqualTo("password");
        assertThat(httpRequest.getParam("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParam("email")).isEqualTo("javajigi@slipp.net");
    }

    @DisplayName("Body 를 확인한다.")
    @Test
    void getBody() {
        HttpRequest requestWithContents = getNormalPostRequest();

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

    @DisplayName("Path 를 확인한다.")
    @Test
    void getPath() {
        assertThat(getNormalGetRequest().getPath()).isEqualTo("/user/create");
    }
}