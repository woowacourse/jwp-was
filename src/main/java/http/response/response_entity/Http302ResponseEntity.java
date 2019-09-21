package http.response.response_entity;

import http.HttpHeaders;
import http.response.HttpStatus;

import static http.response.HttpStatus.FOUND;

public class Http302ResponseEntity implements HttpResponseEntity {
    private String location;

    public Http302ResponseEntity(String location) {
        this.location = location;
    }

    @Override
    public HttpStatus getStatus() {
        return FOUND;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);
        return headers;
    }

    @Override
    public String getViewTemplatePath() {
        return null;
    }
}
