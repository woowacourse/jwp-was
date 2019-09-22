package http.model;

import java.util.Map;

public class HttpResponse {
    private HttpProtocols protocol;
    private HttpStatus httpStatus;
    private HttpHeaders headers;
    private byte[] body;

    public HttpResponse(HttpProtocols protocol, HttpStatus httpStatus, HttpHeaders headers, byte[] body) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpProtocols protocol, HttpStatus httpStatus, HttpHeaders headers) {
        this(protocol, httpStatus, headers, null);
    }

    public HttpProtocols getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public String getHeader(String key) {
        return headers.getHeader(key);
    }
}
