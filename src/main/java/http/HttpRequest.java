package http;

import javax.annotation.Nullable;

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
