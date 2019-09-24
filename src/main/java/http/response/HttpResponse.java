package http.response;

import http.common.HttpStatus;
import http.common.MimeType;
import http.request.HttpRequest;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String LOCATION_KEY = "Location";

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

    public void ok(byte[] body) {
        this.responseBody = ResponseBody.of(body);
        statusLine.setHttpStatus(HttpStatus.OK);
        putHeader(CONTENT_LENGTH_KEY, String.valueOf(body.length));
    }

    public void redirect(String path) {
        statusLine.setHttpStatus(HttpStatus.FOUND);
        putHeader(LOCATION_KEY, path);
    }

    public void addHeaderFromRequest(HttpRequest httpRequest) {
        statusLine.setHttpVersion(httpRequest.getHttpVersion());
        if (httpRequest.isGet()) {
            putHeader(CONTENT_TYPE_KEY, MimeType.findByPath(httpRequest.getPath()).getContentType());
        }
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
