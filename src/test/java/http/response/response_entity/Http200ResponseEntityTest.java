package http.response.response_entity;

import http.HttpHeaders;
import http.response.response_entity.Http200ResponseEntity;
import http.response.response_entity.HttpResponseEntity;
import org.junit.jupiter.api.Test;

import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;

class Http200ResponseEntityTest {
    @Test
    void Http200ResponseEntity_생성() {
        String viewTemplatePath = "/test.html";
        HttpResponseEntity httpResponseEntity = new Http200ResponseEntity(viewTemplatePath);
        HttpHeaders headers = new HttpHeaders();

        assertThat(httpResponseEntity.getStatus()).isEqualTo(OK);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isEqualTo(viewTemplatePath);
    }
}