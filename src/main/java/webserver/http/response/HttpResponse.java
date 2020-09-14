package webserver.http.response;

public class HttpResponse {
    private ResponseStatusLine responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(ResponseStatusLine responseStatus, ResponseHeaders responseHeaders,
        ResponseBody responseBody) {
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public ResponseStatusLine getResponseStatus() {
        return responseStatus;
    }

    public ResponseHeaders getResponseHeader() {
        return responseHeaders;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }
}
