package http.response;

import http.common.HttpStatus;
import http.common.MimeType;

public class HttpResponse {
    private static final String CRLF = "\r\n";

    private ResponseLine responseLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    private HttpResponse(ResponseLine responseLine,
                         ResponseHeader responseHeader,
                         ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of() {
        return new HttpResponse(ResponseLine.of(),
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
        responseLine.setHttpStatus(HttpStatus.FOUND);
        putHeader("Location", path);
    }

    public byte[] getBody() {
        return responseBody.getBody();
    }

    public void sendNotFound() {
        responseLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    @Override
    public String toString() {
        return responseLine + CRLF +
                responseHeader + CRLF;
    }
}
