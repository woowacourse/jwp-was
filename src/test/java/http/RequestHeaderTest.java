package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.NotFoundHeaderException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestHeaderTest {
    private RequestHeader requestHeader;

    @BeforeEach
    public void setUp() {
        requestHeader = new RequestHeader(
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*\n");
    }

    @Test
    @DisplayName("Header에서 Query Parameter 추출")
    public void extractQueryParameter() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        assertThat(requestHeader.extractQueryParameter()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Header 정보에서 key값으로 value를 가져온다")
    public void get() {
        assertThat(requestHeader.get("host")).isEqualTo("localhost:8080");
        assertThat(requestHeader.get("connection")).isEqualTo("keep-alive");
        assertThat(requestHeader.get("accept")).isEqualTo("*/*");
    }

    @Test
    @DisplayName("Header 정보에서 없는 key값으로 value를 가져올 경우 예외 처리")
    public void getException() {
        String notExistKey = "none";
        assertThrows(NotFoundHeaderException.class, () -> requestHeader.get(notExistKey));
    }
}
