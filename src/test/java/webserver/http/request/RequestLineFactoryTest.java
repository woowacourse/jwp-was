package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineFactoryTest {
    @Test
    void 생성_테스트() {
        // given & when
        RequestLine requestLine = RequestLineFactory.generate("GET /test HTTP/1.1");

        // then
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getUrl()).isEqualTo("/test");
        assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }
}
