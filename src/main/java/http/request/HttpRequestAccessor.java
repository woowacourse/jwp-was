package http.request;

import http.Session;

import java.util.Optional;

public interface HttpRequestAccessor {
    boolean hasParameters();

    boolean hasBody();

    HttpMethod getHttpMethod();

    String getPath();

    String getHeader(String key);

    String getParameter(String key);

    HttpBody getBody();

    Optional<Session> getSession(boolean canBeCreated);
}
