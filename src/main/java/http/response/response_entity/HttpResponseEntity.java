package http.response.response_entity;

import http.HttpHeaders;
import http.response.HttpStatus;

public interface HttpResponseEntity {
    HttpStatus getStatus();

    HttpHeaders getHeaders();

    String getViewTemplatePath();
}
