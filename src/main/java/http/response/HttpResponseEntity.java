package http.response;

import http.HttpHeaders;

import static http.response.HttpStatus.*;

public class HttpResponseEntity {
    private HttpStatus status;
    private HttpHeaders headers;
    private String viewTemplatePath;

    private HttpResponseEntity(HttpStatus status, HttpHeaders headers, String viewTemplatePath) {
        this.viewTemplatePath = viewTemplatePath;
        this.status = status;
        this.headers = headers;
    }

    public static HttpResponseEntity get200Response(String viewTemplatePath) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpResponseEntity(OK, headers, viewTemplatePath);
    }

    public static HttpResponseEntity get302Response(String location) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);
        return new HttpResponseEntity(FOUND, headers, null);
    }

    public static HttpResponseEntity get404Response() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpResponseEntity(NOT_FOUND, headers, "/error.html");
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getViewTemplatePath() {
        return viewTemplatePath;
    }

}
