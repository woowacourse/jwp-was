package http.response.response_entity;

import http.HttpHeaders;
import org.junit.jupiter.api.Test;

import static http.response.HttpStatus.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class Http404ResponseEntityTest {
    @Test
    void Http404ResponseEntity_생성() {
        HttpResponseEntity httpResponseEntity = new Http404ResponseEntity();
        HttpHeaders headers = new HttpHeaders();

        assertThat(httpResponseEntity.getStatus()).isEqualTo(NOT_FOUND);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isEqualTo("/error.html");
    }
}