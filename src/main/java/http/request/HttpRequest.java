package http.request;

import javax.annotation.Nullable;

import http.HttpBody;
import http.HttpHeaders;
import http.HttpMethod;
import http.HttpSession;
import http.HttpVersion;

public interface HttpRequest {
    HttpMethod getMethod();

    String getURI();

    HttpVersion getVersion();

    @Nullable
    String getQuery();

    HttpHeaders getHeaders();

    HttpBody getBody();

    HttpSession getSession();
}
