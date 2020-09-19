package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
}