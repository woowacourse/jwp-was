package http;

import javax.annotation.Nullable;

public interface HttpRequest {
    HttpMethod getMethod();

    String getURI();

    @Nullable
    String getQuery();

    HttpHeaders getHeaders();
}
