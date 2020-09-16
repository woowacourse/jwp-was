package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.request.HttpRequestHeaderTest.POST_REQUEST_WITH_BODY;

public class HttpRequestsTest {
    @DisplayName("요청에서 body 값을 파싱했는지 테스트")
    @Test
    void getBodyTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(
                POST_REQUEST_WITH_BODY.getBytes()
        );
        HttpRequests httpRequests = new HttpRequests(inputStream);
        String body = httpRequests.getBody();

        assertThat(body).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
