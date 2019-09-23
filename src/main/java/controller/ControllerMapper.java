package controller;

import http.request.RequestMethod;

public class ControllerMapper {
    private RequestMethod requestMethod;
    private String originalUrlPath;

    public ControllerMapper(RequestMethod requestMethod, String originalUrlPath) {
        this.requestMethod = requestMethod;
        this.originalUrlPath = originalUrlPath;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getOriginalUrlPath() {
        return originalUrlPath;
    }
}
