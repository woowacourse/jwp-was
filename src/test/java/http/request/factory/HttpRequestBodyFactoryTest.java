package http.request.factory;

import http.request.HttpRequestBody;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyFactoryTest {

    @Test
    void create() {
        List<String> lines = Arrays.asList("body line1", "body line2");
        HttpRequestBody httpRequestBody = new HttpRequestBody(lines);
        assertThat(HttpRequestBodyFactory.create(lines)).isEqualTo(httpRequestBody);
    }
}