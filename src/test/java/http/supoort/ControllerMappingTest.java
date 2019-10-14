package http.supoort;

import http.controller.ControllerMapping;
import http.model.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMappingTest {
    @Test
    void 일치() {
        ControllerMapping controllerMapping = new ControllerMapping(HttpMethod.of("GET"), "/index.html");
        assertThat(new ControllerMapping(HttpMethod.of("GET"), "/index.html")).isEqualTo(controllerMapping);
    }

    @Test
    void 불일치() {
        ControllerMapping controllerMapping = new ControllerMapping(HttpMethod.of("GET"), "/index");
        assertThat(new ControllerMapping(HttpMethod.of("GET"), "/index.html")).isNotEqualTo(controllerMapping);
    }
}