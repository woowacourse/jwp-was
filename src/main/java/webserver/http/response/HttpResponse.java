package webserver.http.response;

public class HttpResponse {
    private final HttpResponseHeader httpResponseHeader;
    private byte[] body;

    public HttpResponse(HttpResponseHeader httpResponseHeader) {
        this.httpResponseHeader = httpResponseHeader;
    }

    public HttpResponse(HttpResponseHeader httpResponseHeader, byte[] body) {
        this.httpResponseHeader = httpResponseHeader;
        this.body = body;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }

    public HttpStatus getHttpStatus() {
        return httpResponseHeader.getHttpResponseLine().getHttpStatus();
    }

    public boolean hasBody() {
        return body.length > 0;
    }

    public String getHeaderValue(String key) {
        return httpResponseHeader.getHeaders().get(key);
    }

    public byte[] getBody() {
        return body;
    }

    public HttpResponseLine getHttpResponseLine() {
        return httpResponseHeader.getHttpResponseLine();
    }
}
