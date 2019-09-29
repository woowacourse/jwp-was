package controller;

import controller.controllermapper.ControllerMapper;
import http.request.RequestMethod;

import java.util.Objects;

public class RequestMapping {
    private final RequestMethod allowedRequestMethod;
    private final String allowedUrlPath;

    public RequestMapping(RequestMethod allowedRequestMethod, String allowedUrlPath) {
        this.allowedRequestMethod = allowedRequestMethod;
        this.allowedUrlPath = allowedUrlPath;
    }

    public boolean isCorrectMapping(ControllerMapper controllerMapper) {
        return (controllerMapper.getRequestMethod() == this.allowedRequestMethod) &&
                (controllerMapper.getOriginalUrlPath().equals(this.allowedUrlPath));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return allowedRequestMethod == that.allowedRequestMethod &&
                Objects.equals(allowedUrlPath, that.allowedUrlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowedRequestMethod, allowedUrlPath);
    }
}
