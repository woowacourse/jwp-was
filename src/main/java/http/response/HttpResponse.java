package http.response;

import http.common.HttpStatus;
import http.common.MimeType;

public class HttpResponse {
    private static final String CRLF = "\r\n";

    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    private HttpResponse(StatusLine statusLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of() {
        return new HttpResponse(StatusLine.of(), ResponseHeader.of(), ResponseBody.of());
    }

    public void putHeader(String key, String value) {
        responseHeader.put(key, value);
    }

    public void setResponseBody(byte[] body, String path) {
        this.responseBody = ResponseBody.of(body);
        statusLine.setHttpStatus(HttpStatus.OK);
        putHeader("Content-Type", MimeType.findByPath(path).getContentType());
        putHeader("Content-Length", String.valueOf(body.length));
    }

    public void redirect(String path) {
        statusLine.setHttpStatus(HttpStatus.FOUND);
        putHeader("Location", path);
    }

    public byte[] getBody() {
        return responseBody.getBody();
    }

    public void sendNotFound() {
        statusLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void sendMethodNotAllowed() {
        statusLine.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public String toString() {
        return statusLine + CRLF + responseHeader + CRLF;
    }
}
