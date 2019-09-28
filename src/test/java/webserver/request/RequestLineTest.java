package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {

    private static final String GET = "GET";
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String URI = "/user/hello";
    private static final String PARAMS = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @DisplayName("성공적으로 RequestFirstLine 객체를 생성한다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new RequestLine(String.format("%s %s %s", GET, URI, HTTP_1_1)));
    }

    @DisplayName("RequestFirstLine 객체를 생성에 실패한다.")
    @Test
    void constructorFailed() {
        assertThrows(IllegalArgumentException.class, () -> new RequestLine("ADFAFE " + HTTP_1_1));
    }

    @Test
    void getMethod() {
        RequestLine requestLine = new RequestLine(String.format("%s %s?%s %s", GET, URI, PARAMS, HTTP_1_1));

        assertThat(requestLine.getMethod()).isEqualTo(GET);
        assertThat(requestLine.getFullUri()).isEqualTo(String.format("%s?%s", URI, PARAMS));
        assertThat(requestLine.getVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(requestLine.hasParams()).isTrue();
        assertThat(requestLine.getQueryString()).isEqualTo(PARAMS);
    }


    @Test
    void hasParamsFalse() {
        RequestLine noParamsRequestLine = new RequestLine(String.format("%s %s %s", GET, URI, HTTP_1_1));
        assertThat(noParamsRequestLine.hasParams()).isFalse();
        assertThat(noParamsRequestLine.getQueryString()).isNull();
    }
}