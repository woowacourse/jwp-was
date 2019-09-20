package network;

public class HttpResponse {
    private HttpVersion httpVersion;
    private HttpStatus httpStatus;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse(final HttpVersion httpVersion,
                        final HttpStatus httpStatus,
                        final HttpHeader httpHeader,
                        final HttpResponseBody httpResponseBody) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.httpHeader = httpHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion=" + httpVersion +
                ", httpStatus=" + httpStatus +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
