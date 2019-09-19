package http.response;

public class HttpResponse {
    private HttpStatusLine httpStatusLine;
    private HttpResponseHeader httpResponseHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse(HttpStatusLine httpStatusLine, HttpResponseHeader httpResponseHeader, HttpResponseBody httpResponseBody) {
        this.httpStatusLine = httpStatusLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }
}
