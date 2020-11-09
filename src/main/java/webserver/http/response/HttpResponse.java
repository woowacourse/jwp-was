package webserver.http.response;

public class HttpResponse {
    private HttpStatusLine statusLine;
    private HttpResponseHeaders responseHeaders;
    private byte[] body;

    public HttpResponse() {
        this.statusLine = HttpStatusLine.withDefaultVersion();
        this.responseHeaders = HttpResponseHeaders.ofEmpty();
    }

    public void changeHttpStatus(HttpStatus httpStatus) {
        this.statusLine.changeHttpStatus(httpStatus);
    }

    public void addHeader(String key, String value) {
        this.responseHeaders.addHeader(key, value);
    }

    public HttpStatusLine getStatusLine() {
        return statusLine;
    }

    public HttpResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public byte[] getBody() {
        return body;
    }

    public void addBody(byte[] body) {
        this.body = body;
    }
}
