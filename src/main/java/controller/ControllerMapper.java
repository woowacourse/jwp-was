package controller;

import http.request.RequestMethod;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerMapper that = (ControllerMapper) o;
        return requestMethod == that.requestMethod &&
                Objects.equals(originalUrlPath, that.originalUrlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, originalUrlPath);
    }
}
