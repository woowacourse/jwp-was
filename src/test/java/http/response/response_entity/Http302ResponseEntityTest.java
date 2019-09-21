package http.response.response_entity;

import http.HttpHeaders;
import http.response.response_entity.Http302ResponseEntity;
import http.response.response_entity.HttpResponseEntity;
import org.junit.jupiter.api.Test;

import static http.response.HttpStatus.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class Http302ResponseEntityTest {
    @Test
    void Http302ResponseEntity_생성() {
        String location = "/test.html";
        HttpResponseEntity httpResponseEntity = new Http302ResponseEntity(location);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);

        assertThat(httpResponseEntity.getStatus()).isEqualTo(FOUND);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isNull();
    }
}