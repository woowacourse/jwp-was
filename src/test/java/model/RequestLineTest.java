package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {

    @Test
    @DisplayName("RequestLine of")
    void create() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);

        assertThat(requestLine).isInstanceOf(RequestLine.class);
    }

    @Test
    void isSameMethod() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);

        assertThat(requestLine.isSameMethod(Method.GET)).isTrue();
    }

    @Test
    void generateContentTypeFromRequestUri() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);
        ContentType contentType = requestLine.generateContentTypeFromRequestUri();

        Stream.of(
            assertThat(contentType).isInstanceOf(ContentType.class),
            assertThat(contentType).isEqualTo(ContentType.HTML)
        );
    }

    @Test
    void extractParameters() {
        String line = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);
        Map<String, String> parameters = requestLine.extractParameters();

        Stream.of(
            assertThat(parameters.size()).isEqualTo(4),
            assertThat(parameters.get("userId")).isEqualTo("javajigi"),
            assertThat(parameters.get("password")).isEqualTo("password"),
            assertThat(parameters.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1"),
            assertThat(parameters.get("email")).isEqualTo("javajigi%40slipp.net")
        );
    }

    @Test
    void getRequestUri() {
        String line = "GET /index.html HTTP/1.1";
        RequestLine requestLine = RequestLine.of(line);

        assertThat(requestLine.getRequestUri()).isEqualTo("/index.html");
    }
}
