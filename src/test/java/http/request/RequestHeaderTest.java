package http.request;

import http.exception.NotFoundHeaderException;
import http.request.RequestHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestHeaderTest {
    private RequestHeader requestHeader;

    @BeforeEach
    public void setUp() {
        requestHeader = new RequestHeader(
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*\n");
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
