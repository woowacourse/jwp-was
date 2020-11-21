package http.request;

import javax.annotation.Nullable;

import http.HttpBody;
import http.HttpHeaders;
import http.HttpMethod;

public interface HttpRequest {
    HttpMethod getMethod();

    String getURI();

    String getVersion();

    @Nullable
    String getQuery();

    HttpHeaders getHeaders();

    HttpBody getBody();

    String getCookie();
}
