package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    @Test
    void getHttpMethodTest() {
        HttpMethod httpMethod = HttpMethod.getHttpMethod("GET");

        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }

    @Test
    void 소문자로_입력시_성공() {
        HttpMethod httpMethod = HttpMethod.getHttpMethod("post");

        assertThat(httpMethod).isEqualTo(HttpMethod.POST);
    }
}