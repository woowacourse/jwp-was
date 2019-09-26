package http.response;

import http.HttpHeaders;

import static http.HttpHeaders.LOCATION;
import static http.response.HttpStatus.*;

public class HttpResponseEntity {
    public static final String NOT_FOUND_PATH = "./templates/error/404.html";
    public static final String METHOD_NOT_ALLOWED_PATH = "./templates/error/405.html";

    private HttpStatus status;
    private HttpHeaders headers;
    private String viewPath;

    private HttpResponseEntity(HttpStatus status, HttpHeaders headers, String viewPath) {
        this.viewPath = viewPath;
        this.status = status;
        this.headers = headers;
    }

    public static HttpResponseEntity get200Response(String viewTemplatePath) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpResponseEntity(OK, headers, viewTemplatePath);
    }

    public static HttpResponseEntity get302Response(String location) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(LOCATION, location);
        return new HttpResponseEntity(FOUND, headers, null);
    }

    public static HttpResponseEntity get404Response() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpResponseEntity(NOT_FOUND, headers, NOT_FOUND_PATH);
    }

    public static HttpResponseEntity get405Response() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpResponseEntity(METHOD_NOT_ALLOWED, headers, METHOD_NOT_ALLOWED_PATH);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getViewPath() {
        return viewPath;
    }
}
