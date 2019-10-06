package webserver.pageprovider;

import http.Session;
import http.request.HttpBody;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestAccessor;

import java.util.Optional;

public class PageProviderRequest implements HttpRequestAccessor {
    private final HttpRequest request;

    private PageProviderRequest(HttpRequest request) {
        this.request = request;
    }

    public static PageProviderRequest from(HttpRequest request) {
        return new PageProviderRequest(request);
    }

    @Override
    public boolean hasParameters() {
        return request.hasParameters();
    }

    @Override
    public boolean hasBody() {
        return request.hasBody();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return request.getHttpMethod();
    }

    @Override
    public String getPath() {
        return request.getPath();
    }

    @Override
    public String getHeader(String key) {
        return request.getHeader(key);
    }

    @Override
    public String getParameter(String key) {
        return request.getParameter(key);
    }

    @Override
    public HttpBody getBody() {
        return request.getBody();
    }

    @Override
    public Optional<Session> getSession(boolean canBeCreated) {
        return request.getSession(canBeCreated);
    }
}
