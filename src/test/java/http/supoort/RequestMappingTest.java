package http.supoort;

import http.model.HttpMethod;
import http.model.HttpUri;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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