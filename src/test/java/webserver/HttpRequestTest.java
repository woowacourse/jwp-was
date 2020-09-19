package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("HttpRequest 생성 테스트")
    @Test
    void initTest() throws IOException {
        StringBuilder request = new StringBuilder();
        request.append("GET /user/form.html HTTP/1.1 \n");
        request.append("Host: localhost:8080 \n");
        request.append("Connection: keep-alive \n");
        request.append("Accept: */* \n");

        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.toString().getBytes()));

        assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.isResourcePath("/user/form.html")).isTrue();
    }

    @DisplayName("QueryString(Parameter) 파싱 테스트")
    @Test
    void queryParsingTest() throws IOException {
        StringBuilder request = new StringBuilder();
        request.append("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1 \n");
        request.append("Host: localhost:8080 \n");
        request.append("Connection: keep-alive \n");
        request.append("Accept: */* \n");

        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(request.toString().getBytes()));
        Map<String, String> actual = httpRequest.getParameters();

        assertThat(actual).hasSize(4);
        assertThat(actual.get("userId")).isEqualTo("javajigi");
        assertThat(actual.get("password")).isEqualTo("password");
        assertThat(actual.get("name")).isEqualTo("박재성");
        assertThat(actual.get("email")).isEqualTo("javajigi@slipp.net");
    }
}