package webserver.http.response;

public class HttpResponse {
    private final HttpResponseHeader httpResponseHeader;
    private final byte[] body;

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

    public byte[] getBody() {
        return body;
    }
}
