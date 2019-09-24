package http.request.factory;

import http.request.HttpRequestBody;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyFactoryTest {

    @Test
    void create() {
        HttpRequestBody httpRequestBody = new HttpRequestBody("abc");
        assertThat(HttpRequestBodyFactory.create("abc")).isEqualTo(httpRequestBody);
    }
}