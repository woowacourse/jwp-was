package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    @Test
    void 문자열로_객체_생성되는지_확인() {
        HttpMethod httpMethod = HttpMethod.of("GET");

        assertThat(httpMethod).isEqualTo(HttpMethod.GET);
    }

    @Test
    void 소문자로_입력시_성공() {
        HttpMethod httpMethod = HttpMethod.of("post");

        assertThat(httpMethod).isEqualTo(HttpMethod.POST);
    }
}