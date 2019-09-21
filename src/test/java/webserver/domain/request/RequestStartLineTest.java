package webserver.domain.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.support.RequestHelper;

import static org.assertj.core.api.Assertions.assertThat;

class RequestStartLineTest extends RequestHelper {

    private String[] httpMethodAndPath = requestStartLineInput.split(" ");
    private RequestStartLine requestStartLine;

    @BeforeEach
    void setUp() {
        this.requestStartLine = new RequestStartLine(httpMethodAndPath);
    }

    @Test
    @DisplayName("Http 메서드 정보를 정확하게 저장하는지 확인")
    void getHttpMethod() {
        assertThat(this.requestStartLine.getHttpMethod()).isEqualTo("GET");
    }

    @Test
    @DisplayName("요청 경로 정보를 정확하게 저장하는지 확인")
    void getPath() {
        assertThat(this.requestStartLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Http 버전 정보를 정확하게 저장하는지 확인")
    void getHttpVersion() {
        assertThat(this.requestStartLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}