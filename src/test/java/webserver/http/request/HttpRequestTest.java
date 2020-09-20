package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    @DisplayName("HttpRequest 생성 테스트")
    @Test
    void queryParsingTest() throws IOException {
        // given
        StringBuilder request = new StringBuilder();
        request.append("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n");
        request.append("Host: localhost:8080\n");
        request.append("Connection: keep-alive\n");
        request.append("Accept: */*\n");

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", "javajigi");
        queryParameters.put("password", "password");
        queryParameters.put("name", "박재성");
        queryParameters.put("email", "javajigi@slipp.net");

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");

        // when
        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.toString().getBytes()));

        // then
        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeaders()).containsExactlyEntriesOf(headers);
        assertThat(httpRequest.getQueryParameters()).containsExactlyEntriesOf(queryParameters);
    }

    @DisplayName("RequestBody가 있는 HttpRequest 생성 테스트")
    @Test
    void requestBodyParsingTest() throws IOException {
        // given
        StringBuilder request = new StringBuilder();
        request.append("POST /user/create\n");
        request.append("Host: localhost:8080\n");
        request.append("Connection: keep-alive\n");
        request.append("Accept: */*\n");
        request.append("\n");
        request.append("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n");

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");

        Map<String, String> body = new HashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "박재성");
        body.put("email", "javajigi@slipp.net");

        // when
        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.toString().getBytes()));

        // then
        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeaders()).containsExactlyEntriesOf(headers);
        assertThat(httpRequest.getBody()).containsExactlyEntriesOf(body);
    }
}