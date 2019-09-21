package http.response.response_entity;

import http.HttpHeaders;
import http.response.HttpStatus;

import static http.response.HttpStatus.OK;

public class Http200ResponseEntity implements HttpResponseEntity {
    private String viewTemplatePath;

    public Http200ResponseEntity(String viewTemplatePath) {
        this.viewTemplatePath = viewTemplatePath;
    }

    @Override
    public HttpStatus getStatus() {
        return OK;
    }

    @Override
    public HttpHeaders getHeaders() {
        return new HttpHeaders();
    }

    @Override
    public String getViewTemplatePath() {
        return viewTemplatePath;
    }
}
