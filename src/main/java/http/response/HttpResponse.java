package http.response;

import http.common.HttpHeader;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.response.exception.HttpVersionNotSupportedException;

public class HttpResponse {
    private static final String LINE_FEED_AND_CARRIAGE_RETURN = "\r\n";
    private static final String RESPONSE_HEADER_SERIALIZE_FORMAT = "%s" + LINE_FEED_AND_CARRIAGE_RETURN + "%s";
    private final HttpRequest httpRequest;
    private StatusLine statusLine;
    private final HttpHeader httpHeader;
    private byte[] body;

    public HttpResponse(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        this.httpHeader = new HttpHeader();
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        HttpVersion httpVersion = httpRequest.getHttpVersion();

        this.statusLine = new StatusLine(httpVersion, responseStatus);

        if (httpVersion.isNotSupportedVersion()) {
            throw new HttpVersionNotSupportedException();
        }
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
}
