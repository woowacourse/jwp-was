package webserver.httpRequest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyTest {

    @Test
    void 바디파라미터_확인() {
        String body = "name=java&coach=brown";
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);
        assertThat(httpRequestBody.getBodyParam("name")).isEqualTo("java");
    }
}