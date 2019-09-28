package http.response;

import http.common.HttpHeader;
import http.common.HttpVersion;
import http.request.HttpRequest;

import static com.google.common.net.HttpHeaders.SET_COOKIE;

public class HttpResponse {
    private static final String LINE_FEED_AND_CARRIAGE_RETURN = "\r\n";
    private static final String RESPONSE_HEADER_SERIALIZE_FORMAT = "%s" + LINE_FEED_AND_CARRIAGE_RETURN + "%s";
    private final HttpRequest httpRequest;
    private final HttpHeader httpHeader;
    private StatusLine statusLine;
    private byte[] body;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.httpHeader = new HttpHeader();
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void addHeaderAttribute(String key, String value) {
        this.httpHeader.addHeaderAttribute(key, value);
    }

    public void addCookie(String name, String value) {
        addHeaderAttribute(SET_COOKIE, String.format("%s=%s", name, value));
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

    public ResponseStatus getResponseStatus() {
        return statusLine.getResponseStatus();
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        HttpVersion httpVersion = httpRequest.getHttpVersion();
        this.statusLine = new StatusLine(httpVersion, responseStatus);
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }
}
