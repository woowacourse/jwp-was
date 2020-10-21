package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestTest {
    @DisplayName("HttpRequest 생성 테스트")
    @Test
    void queryParsingTest() throws IOException {
        // given
        StringBuilder request = new StringBuilder();
        request.append("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n");
        request.append("Host: localhost:8080\r\n");
        request.append("Connection: keep-alive\r\n");
        request.append("Accept: */*");

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", "javajigi");
        queryParameters.put("password", "password");
        queryParameters.put("name", "박재성");
        queryParameters.put("email", "javajigi@slipp.net");

        // when
        Reader inputString = new StringReader(URLDecoder.decode(request.toString(), "UTF-8"));
        BufferedReader reader = new BufferedReader(inputString);

        HttpRequest httpRequest = new HttpRequest(reader);

        // then
        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getHeaders().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeaders().get("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeaders().get("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getQueryParameters()).containsExactlyEntriesOf(queryParameters);
    }

    @DisplayName("RequestBody가 있는 HttpRequest 생성 테스트")
    @Test
    void requestBodyParsingTest() throws IOException {
        // given
        StringBuilder request = new StringBuilder();
        request.append("POST /user/create HTTP/1.1\n");
        request.append("Host: localhost:8080\n");
        request.append("Connection: keep-alive\n");
        request.append("Accept: */*\n");
        request.append("\n");
        request.append("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n");

        Map<String, String> body = new HashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "박재성");
        body.put("email", "javajigi@slipp.net");

        // when
        Reader inputString = new StringReader(URLDecoder.decode(request.toString(), "UTF-8"));
        BufferedReader reader = new BufferedReader(inputString);
        HttpRequest httpRequest = new HttpRequest(reader);

        // then
        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getHeaders().get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeaders().get("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeaders().get("Accept")).isEqualTo("*/*");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getBody()).containsExactlyEntriesOf(body);
    }

    @DisplayName("잘못된 입력을 넣었을 때 예외 처리")
    @Test
    void invalidHttpRequestExceptionTest() throws IOException {
        String invalidRequest = "HTTP/1.1 GET /user/create?usertId=javajigi";

        // when
        Reader inputString = new StringReader(invalidRequest);
        BufferedReader reader = new BufferedReader(inputString);

        assertThatThrownBy(() -> new HttpRequest(reader))
                .isInstanceOf(IllegalArgumentException.class);
    }
}