package http.response;

import http.common.HttpHeader;
import http.cookie.Cookie;
import http.request.HttpRequest;

import static http.request.HttpRequest.SESSIONID;

public class HttpResponse {
    private static final String LINE_FEED_AND_CARRIAGE_RETURN = "\r\n";
    private static final String RESPONSE_HEADER_SERIALIZE_FORMAT = "%s" + LINE_FEED_AND_CARRIAGE_RETURN + "%s";
    private StatusLine statusLine;
    private final HttpHeader httpHeader;
    private byte[] body;
    private final HttpRequest httpRequest;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpHeader = new HttpHeader();
        this.httpRequest = httpRequest;
        addSessionCookie();
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.statusLine = new StatusLine(httpRequest.getHttpVersion(), responseStatus);
    }

    private void addSessionCookie() {
        Cookie cookie = new Cookie.Builder(SESSIONID, httpRequest.getSession().getId()).path("/").build();
        httpHeader.addCookie(cookie);
    }

    public void addHeaderAttribute(String key, String value) {
        this.httpHeader.addHeaderAttribute(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public ResponseStatus getResponseStatus() {
        return statusLine.getResponseStatus();
    }

    public byte[] serialize() {
        String responseHeader = String.format(RESPONSE_HEADER_SERIALIZE_FORMAT,
                statusLine.serialize(),
                httpHeader.serialize());

        if (body != null) {
            responseHeader += LINE_FEED_AND_CARRIAGE_RETURN + new String(body);
        }

        return responseHeader.getBytes();
    }

    public byte[] getBody() {
        return this.body;
    }
}
