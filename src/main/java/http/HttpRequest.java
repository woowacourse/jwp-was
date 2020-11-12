package http;

public interface HttpRequest {
    HttpMethod getMethod();

    String getURI();

    HttpHeaders getHeaders();
}
