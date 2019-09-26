package http.response;

import http.common.HttpCookie;
import http.common.HttpStatus;
import http.common.MimeType;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String SET_COOKIE_NAME = "Set-Cookie";

    private ResponseLine responseLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;
    private HttpCookie httpCookie;

    private HttpResponse(ResponseLine responseLine,
                         ResponseHeader responseHeader,
                         ResponseBody responseBody,
                         HttpCookie httpCookie) {

        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
        this.httpCookie = httpCookie;
    }

    public static HttpResponse of(HttpCookie httpCookie) {
        return new HttpResponse(
                ResponseLine.of(),
                ResponseHeader.of(),
                ResponseBody.of(),
                httpCookie
        );
    }

    public HttpCookie getHttpCookie() {
        return httpCookie;
    }

    public void putHeader(String key, String value) {
        responseHeader.put(key, value);
    }

    public void setResponseBody(byte[] body, String sufFix) {
        this.responseBody = ResponseBody.of(body);
        putHeader(ResponseHeader.CONTENT_TYPE_NAME, MimeType.findBySufFix(sufFix).getContentType());
        putHeader(ResponseHeader.CONTENT_LENGTH_NAME, String.valueOf(body.length));
    }

    public void redirect(String path) {
        responseLine.setHttpStatus(HttpStatus.FOUND);
        putHeader(ResponseHeader.LOCATION_NAME, path);
    }

    public byte[] getBody() {
        return responseBody.getBody();
    }

    public void sendNotFound() {
        responseLine.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    public void sendNotAllowed() {
        responseLine.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(responseLine).append(CRLF);
        sb.append(responseHeader);
        if (!httpCookie.toString().equals("")) {
            sb.append(String.format("%s: %s", SET_COOKIE_NAME, httpCookie));
        }
        sb.append(CRLF);
        return sb.toString();
    }
}
