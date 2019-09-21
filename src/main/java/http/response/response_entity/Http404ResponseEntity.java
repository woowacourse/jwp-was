package http.response.response_entity;

import http.HttpHeaders;
import http.response.HttpStatus;

import static http.response.HttpStatus.NOT_FOUND;

public class Http404ResponseEntity implements HttpResponseEntity {
    @Override
    public HttpStatus getStatus() {
        return NOT_FOUND;
    }

    @Override
    public HttpHeaders getHeaders() {
        return new HttpHeaders();
    }

    @Override
    public String getViewTemplatePath() {
        return "/error.html";
    }
}
