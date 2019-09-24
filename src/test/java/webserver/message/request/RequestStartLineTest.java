package webserver.message.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.support.RequestHelper;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestStartLineTest extends RequestHelper {

    private String[] httpMethodAndPath = requestGetStartLine.split(" ");
    private RequestStartLine startLine;

    @BeforeEach
    void setUp() throws URISyntaxException {
        this.startLine = new RequestStartLine(httpMethodAndPath);
    }

    @Test
    @DisplayName("Http 메서드 정보를 정확하게 저장하는지 확인")
    void getHttpMethod() {
        assertThat(this.startLine.getHttpMethod()).isEqualTo("GET");
    }

    @ParameterizedTest
    @ValueSource(strings = {requestGetStartLine, requestGetStartLineWithQuery})
    @DisplayName("요청 Path를 정확하게 저장하는지 확인")
    void getPath(String requestStartLine) throws URISyntaxException {
        RequestStartLine startLine = new RequestStartLine(requestStartLine.split(" "));

        assertThat(startLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("요청 Url를 정확하게 저장하는지 확인")
    void getUrl() throws URISyntaxException {
        RequestStartLine startLine = new RequestStartLine(requestGetStartLineWithQuery.split(" "));

        assertThat(startLine.getUrl()).isEqualTo("/index.html?userId=javajigi&name=pob");
    }

    @Test
    @DisplayName("Http 버전 정보를 정확하게 저장하는지 확인")
    void getHttpVersion() {
        assertThat(this.startLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}