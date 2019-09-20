package http.response;

import http.common.HttpStatus;
import http.common.MimeType;

public class HttpResponse {
    private static final String CRLF = "\r\n";

    private ResponseStartLine responseStartLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    private HttpResponse(ResponseStartLine responseStartLine,
                         ResponseHeader responseHeader,
                         ResponseBody responseBody) {
        this.responseStartLine = responseStartLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of() {
        return new HttpResponse(ResponseStartLine.of(),
                ResponseHeader.of(),
                ResponseBody.of());
    }

    public void putHeader(String key, String value) {
        responseHeader.put(key, value);
    }

    public void setResponseBody(byte[] body, String path) {
        this.responseBody = ResponseBody.of(body);
        putHeader("Content-Type", MimeType.findByPath(path).getContentType());
        putHeader("Content-Length", String.valueOf(body.length));
    }

    public void redirect(String path) {
        responseStartLine.setHttpStatus(HttpStatus.FOUND);
        putHeader("Location", path);
    }

    public byte[] getBody() {
        return responseBody.getBody();
    }

    public void sendNotFound() {
        responseStartLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    @Override
    public String toString() {
        return responseStartLine + CRLF +
                responseHeader + CRLF;
    }
}
