package http.supoort;

import http.model.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {
    @Test
    void 일치() {
        RequestMapping requestMapping = new RequestMapping(HttpMethod.of("GET"), "/index.html");
        assertThat(new RequestMapping(HttpMethod.of("GET"), "/index.html")).isEqualTo(requestMapping);
    }

    @Test
    void 불일치() {
        RequestMapping requestMapping = new RequestMapping(HttpMethod.of("GET"), "/index");
        assertThat(new RequestMapping(HttpMethod.of("GET"), "/index.html")).isNotEqualTo(requestMapping);
    }
}