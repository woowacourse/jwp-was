package http.response;

import http.HttpHeaders;
import org.junit.jupiter.api.Test;

import static http.response.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseEntityTest {
    @Test
    void Http200ResponseEntity_생성() {
        String viewTemplatePath = "/test.html";
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.get200Response(viewTemplatePath);
        HttpHeaders headers = new HttpHeaders();

        assertThat(httpResponseEntity.getStatus()).isEqualTo(OK);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isEqualTo(viewTemplatePath);
    }

    @Test
    void Http302ResponseEntity_생성() {
        String location = "/test.html";
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.get302Response(location);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);

        assertThat(httpResponseEntity.getStatus()).isEqualTo(FOUND);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isNull();
    }

    @Test
    void Http404ResponseEntity_생성() {
        HttpResponseEntity httpResponseEntity = HttpResponseEntity.get404Response();
        HttpHeaders headers = new HttpHeaders();

        assertThat(httpResponseEntity.getStatus()).isEqualTo(NOT_FOUND);
        assertThat(httpResponseEntity.getHeaders()).isEqualTo(headers);
        assertThat(httpResponseEntity.getViewTemplatePath()).isEqualTo("/error.html");
    }
}